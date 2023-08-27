/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： SortEnum.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 排序枚举
 *
 * @author Hedh
 * @since 1.0
 */
@Getter
@ApiModel(value = "排序")
public enum SortEnum {
  /** 降序 */
  @ApiModelProperty(value = "降序")
  DESC,
  /** 升序 */
  @ApiModelProperty(value = "升序")
  ASC,
}
