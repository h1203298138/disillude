/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： MeasureUnitEnum.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.datamodel.enums.MeasureTypeEnum;
import com.hua.disillude.mini.entity.ICN;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 计量单位对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "计量单位")
@EqualsAndHashCode(callSuper = true)
public class MeasureUnit extends ICN {
  private static final long serialVersionUID = -7166198268034152317L;

  @Size(max = LengthConst.NAME, message = "英文名称不能超过" + LengthConst.NAME)
  @ApiModelProperty(value = "英文名称")
  private String engName;
  @NotNull(message = "计量单位类型不能为空")
  @Size(max = LengthConst.TYPE, message = "计量单位类型长度不能超过" + LengthConst.TYPE)
  @ApiModelProperty(value = "计量单位类型", required = true)
  private MeasureTypeEnum type;
}
