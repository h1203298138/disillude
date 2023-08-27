/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： VersionDTO.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 版本标识对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "版本标识对象")
@EqualsAndHashCode(callSuper = true)
public class VersionDTO extends IdDTO {
  @NotNull(message = "版本号不能为空")
  @Max(value = Long.MAX_VALUE, message = "版本号不能超过" + Long.MAX_VALUE)
  @ApiModelProperty(value = "版本号", required = true)
  private Long version;
}