/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： SequenceService.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.sequence;

import com.hua.aroma.commons.jdbc.sql.Expr;
import com.hua.aroma.commons.jdbc.sql.InsertBuilder;
import com.hua.aroma.commons.jdbc.sql.InsertStatement;
import com.hua.aroma.commons.jdbc.sql.Predicates;
import com.hua.aroma.commons.jdbc.sql.SelectBuilder;
import com.hua.aroma.commons.jdbc.sql.SelectStatement;
import com.hua.aroma.commons.jdbc.sql.UpdateBuilder;
import com.hua.aroma.commons.jdbc.sql.UpdateStatement;
import com.hua.disillude.mini.utils.StringUtil;
import com.hua.disillude.template.infrastructure.config.TX;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 使用Redis进行单号生成。
 * <p>
 * 由于Redis是基于内存的Key-Value数据库，当重启后可能导致递增值错误。使用数据库辅助序列增长控制。
 * 由表PMS_SEQUENCE 控制当前序列增长边界。每当达到边界时，对边界进行扩容增长。
 *
 * @author Hedh
 * @since 1.0
 */
@Slf4j
@Component
public class SequenceService {

  /** 默认长度 */
  private static final String SCOPE_PREFIX = "sequence.";
  private static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
  @Autowired
  private StringRedisTemplate redisTemplate;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private int INC_BLOC = 100;
  private int INC_BOUNDARY = 10;

  public String generateByDate(String tenant, String scope, int length, String... prefix) {
    return this.generate(tenant, scope, DEFAULT_DATE_FORMAT, length, prefix);
  }

  public String generate(String tenant, String scope, String dateFormat, int length, String... prefix) {
    StringBuilder builder = new StringBuilder();
    if (prefix != null) {
      for (String p : prefix) {
        if (p != null) {
          builder.append(p);
        }
      }
    }
    builder.append(StringUtil.dateToString(new Date(), dateFormat));
    builder.append(this.generate(tenant, scope, length));
    return builder.toString();
  }

  public String generate(String tenant, String scope, int length, String... prefix) {
    StringBuilder builder = new StringBuilder();
    if (prefix != null) {
      for (String p : prefix) {
        if (p != null) {
          builder.append(p);
        }
      }
    }
    builder.append(this.generate(tenant, scope, length));
    return builder.toString();
  }

  /**
   * 产生一个自增长的单号。
   * 注意，相同scope作用域下的不同长度，其增长是共用的。
   *
   * @param scope
   *     作用域
   * @param length
   *     长度
   */
  public String generate(String tenant, String scope, int length) {
    try {
      scope = SCOPE_PREFIX + scope + StringUtil.dateToString(new Date(), "yyyy-MM-dd");
      String value = this.redisTemplate.opsForValue().get(this.tenantKey(tenant, scope));
      if (value == null) {
        // 考虑到redis重启的情况，如果作用域序列未开始使用，那么从需要从数据库中读取其起始值。
        String lockId = scope + "#start";
        this.lock(tenant, lockId);
        value = this.redisTemplate.opsForValue().get(this.tenantKey(tenant, scope));
        if (value == null) {
          this.startFromStore(tenant, scope);
        }
        this.unlock(tenant, lockId);
      }

      Long seqValue = this.redisTemplate.opsForValue().increment(this.tenantKey(tenant, scope), 1L);
      this.redisTemplate.expire(this.tenantKey(tenant, scope), 1, TimeUnit.DAYS);
      value = String.valueOf(seqValue);
      this.nextFromStore(tenant, scope, seqValue);
      return StringUtils.leftPad(value, length, "0");
    } catch (Exception e) {
      log.error("sequence generate failed!", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * 产生一个自增长的单号。
   * 注意，相同scope作用域下的不同长度，其增长是共用的。
   *
   * @param scope
   *     作用域(不默认按日期区分）
   * @param length
   *     长度
   */
  public String generateByScope(String tenant, String scope, int length) {
    try {
      scope = SCOPE_PREFIX + scope;
      String value = this.redisTemplate.opsForValue().get(this.tenantKey(tenant, scope));
      if (value == null) {
        // 考虑到redis重启的情况，如果作用域序列未开始使用，那么从需要从数据库中读取其起始值。
        String lockId = scope + "#start";
        this.lock(tenant, lockId);
        value = this.redisTemplate.opsForValue().get(this.tenantKey(tenant, scope));
        if (value == null) {
          this.startFromStore(tenant, scope);
        }
        this.unlock(tenant, lockId);
      }

      Long seqValue = this.redisTemplate.opsForValue().increment(this.tenantKey(tenant, scope), 1L);
      // redisTemplate.expire(tenantKey(tenant, scope), 1, TimeUnit.DAYS);
      value = String.valueOf(seqValue);
      this.nextFromStore(tenant, scope, seqValue);
      return StringUtils.leftPad(value, length, "0");
    } catch (Exception e) {
      log.error("sequence generate failed!", e);
      throw new RuntimeException(e);
    }
  }

  public String generateByScope(String tenant, String scope, String format, int length) {
    return this.generateByScope(tenant, scope + StringUtil.dateToString(new Date(), format), length);
  }

  /**
   * 加锁
   */
  private void lock(String tenant, String lockId) {
    do {
      // value = 延时5s。
      Boolean success = this.redisTemplate.opsForValue().setIfAbsent(this.tenantKey(tenant, lockId), "true", 5, TimeUnit.SECONDS);
      if (success == Boolean.TRUE) { // 加锁成功
        break;
      }
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        throw new RuntimeException(e.getMessage());
      }
    } while (true);
  }

  /**
   * 解锁
   */
  private void unlock(String tenant, String lockId) {
    this.redisTemplate.delete(this.tenantKey(tenant, lockId));
  }

  @TX
  public void startFromStore(String tenant, String scope) {
    // 加锁成功后，再判断序列是否启用。仍未启用，则查询数据库序列情况。
    Sequence sequence = this.getScopeSequence(tenant, scope);
    long currentValue = 0;
    String sql;
    if (sequence == null) {
      InsertStatement insert = new InsertBuilder()
          .table("SEQUENCE")
          .addValue("tenant", tenant)
          .addValue("scope", scope)
          .addValue("value", this.INC_BLOC)
          .build();
      this.jdbcTemplate.update(insert);
    } else {
      // 对于数据库已有的记录，当redis上未有对应scope的记录时。认为value前的值都是脏的。
      currentValue = sequence.getValue();
      UpdateStatement update = new UpdateBuilder()
          .table("SEQUENCE")
          .addValue("value", Expr.valueOf("value + " + this.INC_BLOC))
          .where(Predicates.equals("tenant", tenant))
          .where(Predicates.equals("scope", scope))
          .build();
      this.jdbcTemplate.update(update);
    }
    this.redisTemplate.opsForValue().set(this.tenantKey(tenant, scope), String.valueOf(currentValue));
  }

  @TX
  public void nextFromStore(String tenant, String scope, Long value) {
    // 检查序列值是否超过数据库申请区间，若超过，则需要申请下一个序列区间。
    if (this.checkNextFromStore(tenant, scope, value) == false) {
      return;
    }
    String lockId = scope + "#next";
    this.lock(tenant, lockId);
    // 再查一次。因为可能其他段调用的时候已经更新过了。
    if (this.checkNextFromStore(tenant, scope, value) == false) {
      return;
    }
    UpdateStatement update = new UpdateBuilder()
        .table("SEQUENCE")
        .addValue("value", Expr.valueOf("value + " + this.INC_BLOC))
        .where(Predicates.equals("tenant", tenant))
        .where(Predicates.equals("scope", scope))
        .build();
    this.jdbcTemplate.update(update);
    this.unlock(tenant, lockId);
  }

  private boolean checkNextFromStore(String tenant, String scope, Long value) {
    Sequence sequence = this.getScopeSequence(tenant, scope);
    if (sequence == null) {
      InsertStatement insert = new InsertBuilder()
          .table("SEQUENCE")
          .addValue("tenant", tenant)
          .addValue("scope", scope)
          .addValue("value", value)
          .build();
      this.jdbcTemplate.update(insert);
      return true;
    }
    // 设置边界，避免超过。
    return value > sequence.getValue() - this.INC_BOUNDARY;
  }

  private Sequence getScopeSequence(String tenant, String scope) {
    SelectStatement select = new SelectBuilder()
        .from("SEQUENCE")
        .where(Predicates.equals("tenant", tenant))
        .where(Predicates.equals("scope", scope))
        .build();
    List<Sequence> sequences = this.jdbcTemplate.query(select, (rs, rowNum) -> {
      Sequence target = new Sequence();
      target.setScope(rs.getString("scope"));
      target.setValue(rs.getLong("value"));
      return target;
    });
    return sequences.isEmpty() ? null : sequences.get(0);
  }

  public String tenantKey(String tenant, String key) {
    return tenant + "," + key;
  }
}
