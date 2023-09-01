/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： CrudRepository.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.repository;

import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.disillude.mini.entity.TenantEntity;
import com.hua.disillude.mini.exception.BizServiceException;

import java.util.Collections;
import java.util.List;

/**
 * @author Hedh
 * @since 1.0
 */
public interface CrudRepository<T extends TenantEntity> {

  void insert(String tenant, T entity, String... parts);

  default void update(String tenant, T entity, String... parts) throws BizServiceException {
    this.update(tenant, entity, true, parts);
  }

  void update(String tenant, T entity, boolean autoVersion, String... parts) throws BizServiceException;

  void delete(String tenant, T entity, String... parts);

  T get(String tenant, String id, String... parts);

  QueryResult<T> query(String tenant, QueryDefinition qd, String... parts);

  default void fetchParts(String tenant, T target, String... parts) {
    if (target != null) {
      this.fetchParts(tenant, Collections.singletonList(target), parts);
    }
  }

  default void fetchParts(String tenant, List<T> list, String... parts) {

  }

}
