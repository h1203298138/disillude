/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： CrudRepositoryImpl.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.repository;

import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.aroma.commons.jdbc.entity.HasVersion;
import com.hua.aroma.commons.jdbc.executor.JdbcPagingQueryExecutor;
import com.hua.aroma.commons.jdbc.qd.QueryProcessor;
import com.hua.aroma.commons.jdbc.sql.DeleteStatement;
import com.hua.aroma.commons.jdbc.sql.InsertStatement;
import com.hua.aroma.commons.jdbc.sql.Predicates;
import com.hua.aroma.commons.jdbc.sql.SelectStatement;
import com.hua.aroma.commons.jdbc.sql.UpdateStatement;
import com.hua.aroma.commons.jdbc.temapper.TEMapper;
import com.hua.disillude.biz.domain.VersionUtil;
import com.hua.disillude.biz.jdbc.schemas.Schemas;
import com.hua.disillude.biz.jdbc.utils.StatementBuilderFactory;
import com.hua.disillude.mini.entity.TenantEntity;
import com.hua.disillude.mini.exception.BizServiceException;
import com.hua.disillude.template.infrastructure.config.TX;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.hua.disillude.biz.jdbc.utils.StatementBuilderFactory.newDeleteBuilder;
import static com.hua.disillude.biz.jdbc.utils.StatementBuilderFactory.newInsertBuilder;
import static com.hua.disillude.biz.jdbc.utils.StatementBuilderFactory.newSelectBuilder;
import static com.hua.disillude.biz.jdbc.utils.StatementBuilderFactory.newUpdateBuilder;

/**
 * @author Hedh
 * @since 1.0
 */
// @ShardingDb
public class CrudRepositoryImpl<T extends TenantEntity> implements CrudRepository<T> {

  @Autowired
  protected JdbcTemplate jdbcTemplate;
  protected CrudWrapper crudWrapper;

  public CrudRepositoryImpl() {
    CrudRepositorySchema curdSchema = this.getClass().getAnnotation(CrudRepositorySchema.class);
    if (curdSchema == null) {
      throw new RuntimeException("必须设置注解@CrudRepositorySchema");
    }
    try {
      this.crudWrapper = new CrudWrapper();
      this.crudWrapper.setClazz(curdSchema.value());
      this.crudWrapper.setTableName((String) curdSchema.value().getField("TABLE_NAME").get(null));
      Class r = Class.forName(curdSchema.value().getName() + "$R");
      this.crudWrapper.setMapper((TEMapper) r.getField("MAPPER").get(null));
      this.crudWrapper.setQueryProcessor((QueryProcessor) r.getField("QUERY_PROCESSOR").get(null));
    } catch (Exception e) {
      throw new RuntimeException("解析失败", e);
    }
  }

  @TX
  @Override
  public void insert(String tenant, T entity, String... parts) {
    InsertStatement insert = newInsertBuilder()
        .table(this.crudWrapper.getTableName())
        .addValues(this.crudWrapper.getMapper().forInsert(entity))
        .build();
    this.jdbcTemplate.update(insert);
    this.insertParts(entity, parts);
  }

  @TX
  @Override
  public void update(String tenant, T entity, boolean autoVersion, String... parts) throws BizServiceException {
    UpdateStatement update = newUpdateBuilder()
        .table(this.crudWrapper.getTableName())
        .where(Predicates.equals(Schemas.TenantEntity.TENANT, entity.getTenant()))
        .where(Predicates.equals(Schemas.TenantEntity.ID, entity.getId()))
        .build();
    // TODO 不知道会不会有问题
    if (autoVersion && entity instanceof HasVersion) {
      long version = ((HasVersion) entity).getVersion();
      update.addValues(this.crudWrapper.getMapper().forUpdate(entity, true));
      update.where(Predicates.equals(Schemas.VersionEntity.VERSION, version));
      VersionUtil.checkAffectedRows(this.jdbcTemplate.update(update), 1L);
    } else {
      update.addValues(this.crudWrapper.getMapper().forUpdate(entity, Schemas.VersionEntity.VERSION));
      this.jdbcTemplate.update(update);
    }
    this.deleteParts(entity, parts);
    this.insertParts(entity, parts);
  }

  @TX
  @Override
  public void delete(String tenant, T entity, String... parts) {
    DeleteStatement delete = newDeleteBuilder()
        .table(this.crudWrapper.getTableName())
        .where(Predicates.equals(Schemas.TenantEntity.TENANT, tenant))
        .where(Predicates.equals(Schemas.Entity.ID, entity.getId()))
        .build();
    this.jdbcTemplate.update(delete);
    this.deleteParts(entity, parts);
  }

  @Override
  public T get(String tenant, String id, String... parts) {
    if (StringUtils.isBlank(id)) {
      return null;
    }

    SelectStatement select = newSelectBuilder()
        .from(this.crudWrapper.getTableName())
        .where(Predicates.equals(Schemas.TenantEntity.TENANT, tenant))
        .where(Predicates.equals(Schemas.Entity.ID, id))
        .build();
    List<T> list = this.jdbcTemplate.query(select, this.crudWrapper.getMapper());
    T target = list.isEmpty() ? null : list.get(0);

    if (parts != null && parts.length > 0) {
      this.fetchParts(tenant, target, parts);
    }
    return target;
  }

  @Override
  public QueryResult<T> query(String tenant, QueryDefinition qd, String... parts) {
    SelectStatement select = this.crudWrapper.getQueryProcessor().process(qd);
    select.setDialect(StatementBuilderFactory.detect(this.jdbcTemplate));
    select.where(Predicates.equals(select.getFromClause().getAlias(), Schemas.TenantEntity.TENANT, tenant));
    JdbcPagingQueryExecutor<T> queryExecutor = new JdbcPagingQueryExecutor<>(this.jdbcTemplate, this.crudWrapper.getMapper());
    QueryResult<T> queryResult = queryExecutor.query(select, qd.getPage(), qd.getPageSize());

    if (queryResult.getRecords().isEmpty() == false && parts != null && parts.length > 0) {
      this.fetchParts(tenant, queryResult.getRecords(), parts);
    }
    return queryResult;
  }

  protected void insertParts(T entity, String... parts) {
  }

  protected void deleteParts(T entity, String... parts) {
  }
}
