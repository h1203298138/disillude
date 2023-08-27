/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： MeasureTypeEnum.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 计量类型枚举
 *
 * @author Hedh
 * @since 1.0
 */
@Getter
@ApiModel(value = "计量类型")
public enum MeasureTypeEnum {
  /** 数量 */
  @ApiModelProperty(value = "数量")
  QTY,
  /** 重量 */
  @ApiModelProperty(value = "重量")
  WEIGHT,
  /** 长度 */
  @ApiModelProperty(value = "长度")
  LENGTH,
  /** 面积 */
  @ApiModelProperty(value = "面积")
  AREA,
  /** 体积 */
  @ApiModelProperty(value = "体积")
  VOLUME,
  /** 容积 */
  @ApiModelProperty(value = "容积")
  CAPACITY,
}
