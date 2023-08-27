/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： LogActions.java
 * 模块说明：
 * 修改历史：
 * 2022年07月05日 - Hedh - 创建。
 */
package com.hua.disillude.biz.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 日志动作
 *
 * @author Hedh
 * @since 1.0
 */
@ApiModel(value = "日志动作")
public abstract class LogActions {
  @ApiModelProperty("新建")
  public static final String CREATE = "create";
  @ApiModelProperty("编辑")
  public static final String MODIFY = "modify";
  @ApiModelProperty("启用")
  public static final String ENABLE = "enable";
  @ApiModelProperty("禁用")
  public static final String DISABLE = "disable";
  @ApiModelProperty("提交")
  public static final String SUBMIT = "submit";
  @ApiModelProperty("驳回")
  public static final String REJECT = "reject";
  @ApiModelProperty("审核")
  public static final String AUDIT = "audit";
  @ApiModelProperty("作废")
  public static final String ABORT = "abort";
  @ApiModelProperty("删除")
  public static final String DELETE = "delete";
  @ApiModelProperty("生效")
  public static final String EFFECT = "effect";
}