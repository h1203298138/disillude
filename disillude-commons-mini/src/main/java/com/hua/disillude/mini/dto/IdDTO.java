/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： IdDTO.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.dto;

import com.hua.disillude.mini.constant.LengthConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * ID标识对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "ID标识")
public class IdDTO {
  @NotBlank(message = "ID标识不能为空")
  @Size(max = LengthConst.ID, message = "ID标识长度不能超过" + LengthConst.ID)
  @ApiModelProperty(value = "ID标识", example = "0001", required = true)
  private String id;
}