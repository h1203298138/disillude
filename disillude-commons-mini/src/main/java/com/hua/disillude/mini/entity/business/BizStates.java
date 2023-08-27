/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： BizStates.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 业务状态
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "业务状态")
public class BizStates implements Serializable {
  private static final long serialVersionUID = -7608502828438447270L;

  @ApiModelProperty("未提交")
  public static final String INITIAL = "initial";
  @ApiModelProperty("已提交")
  public static final String SUBMITTED = "submitted";
  @ApiModelProperty("已驳回")
  public static final String REJECTED = "rejected";
  @ApiModelProperty("已生效")
  public static final String EFFECT = "effect";
  @ApiModelProperty("已完成")
  public static final String FINISHED = "finished";
  @ApiModelProperty("已作废")
  public static final String ABORTED = "aborted";
  @ApiModelProperty("已删除")
  public static final String DELETED = "deleted";
}