/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： LogAdapter.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.log;

import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.aroma.commons.jdbc.executor.JdbcPagingQueryExecutor;
import com.hua.aroma.commons.jdbc.qd.QueryProcessor;
import com.hua.aroma.commons.jdbc.sql.InsertBuilder;
import com.hua.aroma.commons.jdbc.sql.InsertStatement;
import com.hua.aroma.commons.jdbc.sql.Predicates;
import com.hua.aroma.commons.jdbc.sql.SelectStatement;
import com.hua.aroma.commons.jdbc.temapper.TEMapper;
import com.hua.disillude.biz.access.AccessContext;
import com.hua.disillude.biz.jdbc.schemas.Schemas;
import com.hua.disillude.biz.multitenant.TenantContext;
import com.hua.disillude.biz.spring.ServiceFactory;
import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.entity.LogEntity;
import com.hua.disillude.mini.entity.TenantEntity;
import com.hua.disillude.mini.entity.hasinfo.HasAppId;
import com.hua.disillude.mini.entity.hasinfo.HasCreateInfo;
import com.hua.disillude.mini.entity.hasinfo.HasId;
import com.hua.disillude.mini.entity.hasinfo.HasLastModifiedInfo;
import com.hua.disillude.mini.utils.generator.IdGenerator;
import com.hua.disillude.template.infrastructure.config.TX;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.constraints.NotNull;

import static com.hua.disillude.biz.i18n.LocaleMessage.$t;

/**
 * @author Hedh
 * @since 1.0
 */
@Slf4j
public class LogAdapter<E> {
  public static final String DEFAULT_TENANT = "-";

  private final String tableName;
  private final QueryProcessor queryProcessor;
  private final TEMapper teMapper;

  public LogAdapter(String tableName, TEMapper teMapper, QueryProcessor queryProcessor) {
    this.tableName = tableName;
    this.teMapper = teMapper;
    this.queryProcessor = queryProcessor;
  }

  public void log(@NotNull HasId<String> payload, String action) {
    OperateInfo operateInfo = null;
    if (payload instanceof HasLastModifiedInfo) {
      operateInfo = ((HasLastModifiedInfo) payload).getLastModifiedInfo();
    } else if (payload instanceof HasCreateInfo) {
      operateInfo = ((HasCreateInfo) payload).getCreateInfo();
    }
    this.log(payload, action, operateInfo);
    log.info("");
  }

  public void log(@NotNull HasId<String> payload, String action, OperateInfo operateInfo) {
    LogEntity log = new LogEntity();
    log.setId(IdGenerator.nextIid());
    log.setTenant(payload instanceof TenantEntity ? ((TenantEntity) payload).getTenant() : DEFAULT_TENANT);
    if (AccessContext.getAccessUser() != null) {
      log.setAppId(AccessContext.getAccessUser().getAppId());
    } else if (payload instanceof HasAppId) {
      log.setAppId(((HasAppId) payload).getAppId());
    }
    log.setAction(action);
    log.setActionName($t(action));
    log.setRefObjectId(payload.getId());
    this.log(log.getTenant(), log, operateInfo);
  }

  public <T extends LogEntity> void log(String tenant, T log, OperateInfo operateInfo) {
    TenantContext.setTenant(tenant);
    log.setTenant(tenant);
    log.setOperateInfo(operateInfo);
    this.log(log);
  }

  @TX
  public <T extends LogEntity> void log(T log) {
    assert log.getAppId() != null;
    InsertStatement insert = new InsertBuilder()
        .table(this.tableName)
        .addValues(this.teMapper.forInsert(log))
        .build();
    this.getJdbcTemplate().update(insert);
  }

  public <T extends LogEntity> QueryResult<T> query(String tenant, QueryDefinition qd) {
    SelectStatement select = this.queryProcessor.process(qd);
    select.where(Predicates.equals(Schemas.TenantEntity.TENANT, tenant));
    JdbcPagingQueryExecutor<T> queryExecutor = new JdbcPagingQueryExecutor<>(this.getJdbcTemplate(), this.teMapper);
    return queryExecutor.query(select, qd.getPage(), qd.getPageSize());
  }

  private JdbcTemplate getJdbcTemplate() {
    return ServiceFactory.getService(JdbcTemplate.class);
  }
}
