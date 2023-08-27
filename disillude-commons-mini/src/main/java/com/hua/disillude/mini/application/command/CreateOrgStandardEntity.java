/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： CreateOrgStandardEntity.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.application.command;

import com.hua.disillude.mini.constant.LengthConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "创建组织标准实体")
@EqualsAndHashCode(callSuper = true)
public abstract class CreateOrgStandardEntity extends CreateTenantStandardEntity {
  @NotBlank
  @Length(max = LengthConst.ID)
  @ApiModelProperty(value = "所属组织标识", required = true)
  private String orgId;
}
