/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： StatementBuilderFactory.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.biz.jdbc.utils;

import com.hua.aroma.commons.jdbc.dialect.BeanPostProcessorDialectDetector;
import com.hua.aroma.commons.jdbc.dialect.DetectDialectException;
import com.hua.aroma.commons.jdbc.dialect.Dialect;
import com.hua.aroma.commons.jdbc.sql.DeleteBuilder;
import com.hua.aroma.commons.jdbc.sql.InsertBuilder;
import com.hua.aroma.commons.jdbc.sql.InsertSelectBuilder;
import com.hua.aroma.commons.jdbc.sql.MultilineInsertBuilder;
import com.hua.aroma.commons.jdbc.sql.SelectBuilder;
import com.hua.aroma.commons.jdbc.sql.UpdateBuilder;
import com.hua.aroma.commons.jdbc.sql.UpsertBuilder;
import com.hua.disillude.biz.multitenant.TenantContext;
import com.hua.disillude.biz.spring.ServiceFactory;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
public class StatementBuilderFactory {
  private static JdbcTemplate jdbcTemplate;
  private static final ConcurrentMap<Object, Dialect> dialectMap = new ConcurrentHashMap<>();

  private static JdbcTemplate getJdbcTemplate() {
    if (jdbcTemplate == null) {
      synchronized (StatementBuilderFactory.class) {
        jdbcTemplate = ServiceFactory.getService(JdbcTemplate.class);
      }
    }
    return jdbcTemplate;
  }

  public StatementBuilderFactory() {
  }

  public static SelectBuilder newSelectBuilder() {
    return new SelectBuilder(detect(getJdbcTemplate()));
  }

  public static SelectBuilder newSelectBuilder(JdbcTemplate jdbcTemplate) {
    return new SelectBuilder(detect(jdbcTemplate));
  }

  public static MultilineInsertBuilder newMultilineInsertBuilder() {
    return new MultilineInsertBuilder(detect(getJdbcTemplate()));
  }

  public static InsertBuilder newInsertBuilder() {
    return new InsertBuilder(detect(getJdbcTemplate()));
  }

  public static InsertSelectBuilder newInsertSelectBuilder() {
    return new InsertSelectBuilder(detect(getJdbcTemplate()));
  }

  public static UpdateBuilder newUpdateBuilder() {
    return new UpdateBuilder(detect(getJdbcTemplate()));
  }

  public static UpsertBuilder newUpsertBuilder() {
    return new UpsertBuilder(detect(getJdbcTemplate()));
  }

  public static DeleteBuilder newDeleteBuilder() {
    return new DeleteBuilder(detect(getJdbcTemplate()));
  }

  public static Dialect detect(JdbcTemplate template) throws DetectDialectException {
    if (template == null) {
      return null;
    }
    TenantContext sc = TenantContext.get();
    if (sc == null || sc.getTenant() == null) {
      return BeanPostProcessorDialectDetector.detect(getJdbcUrl(template.getDataSource()));
    }
    return dialectMap.computeIfAbsent(sc.getTenant(), k ->
        BeanPostProcessorDialectDetector.detect(getJdbcUrl(template.getDataSource())));
  }

  private static String getJdbcUrl(DataSource dataSource) throws DetectDialectException {
    assert dataSource != null;

    try {
      Class type = dataSource.getClass();

      Method method;
      try {
        // noinspection JavaReflectionMemberAccess
        method = type.getMethod("getUrl");
      } catch (NoSuchMethodException e) {
        // noinspection JavaReflectionMemberAccess
        method = type.getMethod("getJdbcUrl");
      }
      return (String) method.invoke(dataSource);
    } catch (Exception e) {
      throw new DetectDialectException(e);
    }
  }
}
