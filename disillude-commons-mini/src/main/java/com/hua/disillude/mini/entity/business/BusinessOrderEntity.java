/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： BusinessOrderEntity.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.business;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.OrgStandardEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 业务单据实体
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@NoArgsConstructor
@ApiModel(value = "业务单据实体")
@EqualsAndHashCode(callSuper = true)
public class BusinessOrderEntity extends OrgStandardEntity implements HasBizState {
  private static final long serialVersionUID = -7161436565866737842L;

  @NotBlank(message = "单据状态不能为空")
  @Size(max = LengthConst.BIZ_STATE, message = "单据状态长度不能超过" + LengthConst.BIZ_STATE)
  @ApiModelProperty(value = "单据状态", required = true)
  private String bizState;
}
