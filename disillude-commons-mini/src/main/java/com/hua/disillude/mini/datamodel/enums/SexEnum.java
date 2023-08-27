/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： SexEnum.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author Hedh
 * @since 1.0
 */
@Getter
@ApiModel(value = "性别")
public enum SexEnum {
  /** 男性 */
  @ApiModelProperty(value = "男性")
  MALE,
  /** 女性 */
  @ApiModelProperty(value = "女性")
  FEMALE,
  /** 保密 */
  @ApiModelProperty(value = "保密")
  UNKNOWN
}
