/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： BizActions.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 业务动作集
 *
 * @author Hedh
 * @since 1.0
 */
@ApiModel(value = "业务动作集")
public class BizActions {
  @ApiModelProperty("编辑")
  public static final String MODIFY = "modify";
  @ApiModelProperty("删除")
  public static final String DELETE = "delete";
  @ApiModelProperty("驳回")
  public static final String REJECT = "reject";
  @ApiModelProperty("提交")
  public static final String SUBMIT = "submit";
  @ApiModelProperty("生效")
  public static final String EFFECT = "effect";
  @ApiModelProperty("作废")
  public static final String ABORT = "abort";
  @ApiModelProperty("完成")
  public static final String FINISH = "finish";
}