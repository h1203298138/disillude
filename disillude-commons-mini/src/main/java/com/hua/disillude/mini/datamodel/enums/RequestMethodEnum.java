/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： RequestMethodEnum.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求方式枚举
 *
 * @author Hedh
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@ApiModel(value = "请求方式")
public enum RequestMethodEnum {
  /** Get请求 */
  GET(false),
  /** PUT请求 */
  PUT(true),
  /** POST请求 */
  POST(true),
  /** DELETE请求 */
  DELETE(false),
  /** HEAD请求 */
  HEAD(false),
  /** OPTIONS请求 */
  OPTIONS(false);

  @ApiModelProperty(value = "是否有请求体")
  private final boolean hasContent;
}
