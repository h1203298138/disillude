/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： BusinessOrderService.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.domain;

import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.entity.business.BizFlow;
import com.hua.disillude.mini.entity.business.BusinessOrderEntity;
import com.hua.disillude.mini.exception.BizServiceException;

/**
 * 业务单据操作接口定义
 *
 * @author Hedh
 * @since 1.0
 */
public interface BusinessOrderService<T extends BusinessOrderEntity> extends CrudService<T> {
  /** 当前状态不允许执行该动作 */
  String ERROR_SRC_STATE = "error.srcState";
  String ERROR_BIZ_STATE = "error.bizState";

  /** 执行业务动作 */
  T execute(String id, long version, String action, OperateInfo operateInfo) throws BizServiceException;

  /** 获取业务动作流 */
  BizFlow bizFlow();
}
