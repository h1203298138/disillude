/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： DimensionSize.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 尺寸大小
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "尺寸大小")
public class DimensionSize {
  @ApiModelProperty("长")
  private BigDecimal x;
  @ApiModelProperty("宽")
  private BigDecimal y;
  @ApiModelProperty("高")
  private BigDecimal z;
  @ApiModelProperty("体积")
  private BigDecimal volume;
}
