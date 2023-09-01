/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： LogAdapterBuilder.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.log;

import com.hua.aroma.commons.jdbc.annotation.TableName;
import com.hua.aroma.commons.jdbc.qd.QueryProcessor;
import com.hua.aroma.commons.jdbc.qd.QueryProcessorBuilder;
import com.hua.aroma.commons.jdbc.temapper.TEMapper;
import com.hua.aroma.commons.jdbc.temapper.TEMapperBuilder;
import com.hua.aroma.commons.mini.lang.Assert;
import com.hua.disillude.biz.jdbc.schemas.Schemas;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hedh
 * @since 1.0
 */
public class LogAdapterBuilder<E> {
  private static final Map<String, LogAdapter> ADAPTER_MAP = new ConcurrentHashMap();

  private String name;
  private final List<String> alias = new ArrayList<>();
  private final Class<? extends E> entityType;
  private final Class<?> schemaType;
  private final Set<String> primaryKeys = new HashSet<>();

  /**
   * @param entityType
   *     实体类型。
   * @param schemaType
   *     schema定义类型。
   */
  public static <E> LogAdapterBuilder<E> of(@NotNull Class<? extends E> entityType, @NotNull Class<?> schemaType) {
    return new LogAdapterBuilder(entityType, schemaType);
  }

  LogAdapterBuilder(@NotNull Class<? extends E> entityType, @NotNull Class<?> schemaType) {
    Assert.notNull(entityType, "entityType");
    Assert.notNull(schemaType, "schemaType");

    this.entityType = entityType;
    this.schemaType = schemaType;
    this.primaryKeys.add(Schemas.LogEntity.ID);
  }

  /**
   *
   */
  public LogAdapterBuilder<E> name(String name) {
    this.name = name;
    return this;
  }

  public LogAdapterBuilder<E> alias(String... alias) {
    this.alias.clear();
    if (alias != null) {
      this.alias.addAll(Arrays.asList(alias));
    }
    return this;
  }

  public LogAdapterBuilder<E> primaryKey(String... columnNames) {
    this.primaryKeys.clear();
    if (columnNames != null) {
      this.primaryKeys.addAll(Arrays.asList(columnNames));
    }
    return this;
  }

  public LogAdapter<E> build() {
    if (this.name == null) {
      throw new RuntimeException("name 必须设置");
    } else if (ADAPTER_MAP.containsKey(this.name)) {
      throw new RuntimeException("name 发生冲突");
    }
    TEMapper teMapper = TEMapperBuilder
        .of(this.entityType, this.schemaType)
        .primaryKey(this.primaryKeys.toArray(new String[]{}))
        .build();
    QueryProcessor queryProcessor = new QueryProcessorBuilder(this.entityType, this.schemaType).build();
    LogAdapter logAdapter = new LogAdapter(this.getTableName(), teMapper, queryProcessor);
    ADAPTER_MAP.put(this.name, logAdapter);
    return logAdapter;
  }

  public LogAdapter<E> getOrBuild() {
    if (this.name == null) {
      throw new RuntimeException("name 必须设置");
    } else if (ADAPTER_MAP.containsKey(this.name)) {
      LogAdapter logAdapter = ADAPTER_MAP.get(this.name);
      for (String alias : this.alias) {
        if (ADAPTER_MAP.containsKey(alias)) {
          // 该别名已存在适配器时，查看该适配器是否与当前适配器一致，不一致表示别名重复设置适配器了
          LogAdapter aliasLogAdapter = ADAPTER_MAP.get(alias);
          if (aliasLogAdapter != logAdapter) {
            throw new RuntimeException(MessageFormat.format("别名：{0} 已存在对应日志适配器，不可为其重复设置日志适配器", alias));
          }
        }
        ADAPTER_MAP.put(alias, logAdapter);
      }
      return logAdapter;
    }
    TEMapper teMapper = TEMapperBuilder
        .of(this.entityType, this.schemaType)
        .primaryKey(this.primaryKeys.toArray(new String[]{}))
        .build();
    QueryProcessor queryProcessor = new QueryProcessorBuilder(this.entityType, this.schemaType).build();
    LogAdapter logAdapter = new LogAdapter(this.getTableName(), teMapper, queryProcessor);
    ADAPTER_MAP.put(this.name, logAdapter);
    this.alias.forEach(k -> ADAPTER_MAP.put(k, logAdapter));
    return logAdapter;
  }

  private String getTableName() {
    try {
      Field field = this.schemaType.getDeclaredField(TableName.DEFAULT_IDENTIFIER);
      return (String) this.schemaType.getField(TableName.DEFAULT_IDENTIFIER).get(null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static LogAdapter getLogAdapter(String name) {
    return ADAPTER_MAP.get(name);
  }
}
