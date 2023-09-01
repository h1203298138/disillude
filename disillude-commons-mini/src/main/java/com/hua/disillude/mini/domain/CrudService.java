/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： CrudService.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.domain;

import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.entity.TenantEntity;
import com.hua.disillude.mini.exception.BizServiceException;

/**
 * 标准接口定义
 *
 * @author Hedh
 * @since 1.0
 */
public interface CrudService<T extends TenantEntity> {

  /**
   * 分页查询
   */
  QueryResult<T> query(String tenant, QueryDefinition qd, String... parts);

  /**
   * 获取数据
   */
  T get(String tenant, String id, String... parts);

  /**
   * 安全查询
   */
  T safeGet(String tenant, String id, String... parts) throws BizServiceException;

  /**
   * 删除指定标识的数据
   */
  void delete(String tenant, String id, Long version, OperateInfo operateInfo) throws BizServiceException;
}
