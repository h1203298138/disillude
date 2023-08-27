/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： RedisDistributedLocker.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.redis;

import com.hua.disillude.mini.exception.BizServiceException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.hua.disillude.biz.i18n.LocaleMessage.$t;

/**
 * redis 分布式锁，自动续期
 *
 * @author Hedh
 * @since 1.0
 */
@Slf4j
@Service
public class RedisDistributedLocker {

  public static final String RESOURCE_LOCKED = "resource.locked";
  public static final String RESOURCE_BUSY = "resource.busy";
  public static final String RESOURCE_EXCEPTION = "resource.exception";

  /**
   * 每个key的过期时间 {@link RedisLockContent}
   */
  private Map<String, RedisLockContent> lockerContents = new ConcurrentHashMap<>(512);

  @Autowired
  private StringRedisTemplate redisTemplate;

  public RedisDistributedLocker() {
    ScheduleTask task = new ScheduleTask(this.lockerContents);

    long delay = TimeUnit.SECONDS.toMillis(1);
    long period = TimeUnit.SECONDS.toMillis(1);
    // 定时执行
    new Timer("Lock-Renew-Task").schedule(task, delay, period);
  }

  public RedisLockContent lock(String lockKey, LockPolicy policy) throws BizServiceException {
    String lockValue = ObjectUtils.firstNonNull(MDC.get("trace_id"), String.valueOf(Thread.currentThread().getId()));
    return this.lock(lockKey, lockValue, policy);
  }

  public BatchLockContent batchLock(Collection<String> lockKeys, LockPolicy policy) throws BizServiceException {
    String lockValue = UUID.randomUUID().toString();
    BatchLockContent result = new BatchLockContent();
    for (String lockKey : lockKeys) {
      try (RedisLockContent item = this.lock(lockKey, lockValue, policy)) {
        result.getLockContents().add(item);
      }
    }
    return result;
  }

  public RedisLockContent lock(String lockKey, String lockValue, LockPolicy policy) throws BizServiceException {
    log.info("开始执行加锁, lockKey ={}", lockKey);
    for (int i = 0; i < 50; i++) {  // 尝试加锁50 * 100ms = 5s TODO 过期时间，明细行上线
      // 判断是否已经有线程持有锁，减少redis的压力
      RedisLockContent lockContent = new RedisLockContent(this);
      lockContent.setLockKey(lockKey);
      lockContent.setLockValue(lockValue);
      lockContent.setThread(Thread.currentThread());

      RedisLockContent lockedContent = this.lockerContents.computeIfAbsent(lockKey, k -> {
        Boolean success = this.redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 5, TimeUnit.SECONDS);
        if (success == Boolean.TRUE) {
          log.info("加锁成功, lockKey ={}, requestId={}", lockKey, lockValue);
          return lockContent;
        }
        return null;
      });

      // 如果没有被锁，就获取锁
      if (lockedContent == null) {
        if (policy == LockPolicy.exception) {
          throw new BizServiceException($t(RESOURCE_EXCEPTION));
        }
      } else if (Thread.currentThread() == lockedContent.getThread()
          && lockValue.equals(lockedContent.getLockValue())) {
        // 重复获取锁，在线程池中由于线程复用，线程相等并不能确定是该线程的锁
        return lockedContent;
      }

      if (policy == LockPolicy.exception) {
        throw new BizServiceException($t(RESOURCE_LOCKED));
      }
      // 如果被锁或获取锁失败，则等待100毫秒
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        log.error("获取redis 锁失败, lockKey =" + lockKey, e);
        Thread.currentThread().interrupt();
        throw new BizServiceException($t(RESOURCE_BUSY));
      }
    }
    throw new BizServiceException($t(RESOURCE_BUSY));
  }

  /**
   * 解锁
   */
  public void unlock(String lockKey, String lockValue) {
    log.info("释放加锁, lockKey ={}", lockKey);
    RedisLockContent lockContent = this.lockerContents.get(lockKey);

    long consumeTime;
    if (lockContent == null) {
      return;
    } else if (lockValue.equals(lockContent.getLockValue()) == false) {
      throw new RuntimeException($t(RESOURCE_EXCEPTION));
    }

    this.redisTemplate.delete(lockKey);
    this.lockerContents.remove(lockKey);
  }

  /**
   * 续约
   */
  public boolean renew(RedisLockContent lockContent) {
    // 检测执行业务线程的状态
    Thread.State state = lockContent.getThread().getState();
    if (Thread.State.TERMINATED == state) {
      this.unlock(lockContent.getLockKey(), lockContent.getLockValue());
      log.debug("执行业务的线程已终止,不再续约 lockKey ={}, lockContent={}", lockContent.getLockKey(), lockContent.getLockValue());
      return false;
    }

    Boolean success = this.redisTemplate.expire(lockContent.getLockKey(), 5, TimeUnit.SECONDS);
    return success == Boolean.TRUE;
  }

  public static class ScheduleTask extends TimerTask {

    private final Map<String, RedisLockContent> lockContentMap;

    public ScheduleTask(Map<String, RedisLockContent> lockContentMap) {
      this.lockContentMap = lockContentMap;
    }

    @SneakyThrows
    @Override
    public void run() {
      if (this.lockContentMap.isEmpty()) {
        return;
      }

      Set<Map.Entry<String, RedisLockContent>> entries = this.lockContentMap.entrySet();
      for (RedisLockContent lockContent : this.lockContentMap.values()) {
        lockContent.renew();
      }
    }
  }

  public static void main(String[] args) throws BizServiceException, InterruptedException {
    RedisDistributedLocker distributedLocker = new RedisDistributedLocker();
    String lockKey = "";
    // try (RedisLockContent lockValue = distributedLocker.lock(lockKey, LockPolicy.wait)) {
    //   log.info("执行");
    // }

    log.info("123");
    new Thread(() -> {
      RedisLockContent target = new RedisLockContent(distributedLocker);
      target.setLockKey("123");
      target.setLockValue("123");
      log.info("{}", distributedLocker.test(target));
    });
    Thread.sleep(1000);
    new Thread(() -> {
      RedisLockContent target = new RedisLockContent(distributedLocker);
      target.setLockKey("123");
      target.setLockValue("456");
      log.info("{}", distributedLocker.test(target));
    });
  }

  public RedisLockContent test(RedisLockContent target) {
    log.info("test = {}", target.getLockValue());
    return this.lockerContents.computeIfAbsent(target.getLockKey(), k -> {
      if (target.getLockValue().equals("123")) {
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
      }
      return target;
    });
  }
}
