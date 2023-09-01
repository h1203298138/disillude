/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： OrgStandardEntity.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.disillude.mini.constant.LengthConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 组织标准实体
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@NoArgsConstructor
@ApiModel(value = "组织标准实体")
@EqualsAndHashCode(callSuper = true)
public abstract class OrgStandardEntity extends TenantStandardEntity {
  private static final long serialVersionUID = -2652813217039333183L;

  @NotBlank(message = "组织标识不能为空")
  @Size(max = LengthConst.ID, message = "组织标识长度不能超过" + LengthConst.ID)
  @ApiModelProperty(value = "组织标识", example = "9999", required = true)
  private String orgId;
}