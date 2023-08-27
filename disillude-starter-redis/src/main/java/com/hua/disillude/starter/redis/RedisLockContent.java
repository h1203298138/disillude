/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： RedisLockContent.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.redis;

import com.hua.disillude.mini.exception.BizServiceException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RedisLockContent extends LockContent {
  private Thread thread;
  private RedisDistributedLocker distributedLocker;

  public RedisLockContent(RedisDistributedLocker distributedLocker) {
    this.distributedLocker = distributedLocker;
  }

  public boolean renew() throws BizServiceException {
    // 减少线程池中任务数量
    boolean success = this.distributedLocker.renew(this);
    if (success == false) {// 续约失败，说明已经执行完 OR redis 出现问题
      this.distributedLocker.unlock(this.getLockKey(), this.getLockValue());
    }
    return success;
  }

  @Override
  public void close() {
    this.distributedLocker.unlock(this.getLockKey(), this.getLockValue());
  }
}
