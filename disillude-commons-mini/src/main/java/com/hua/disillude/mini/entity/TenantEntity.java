/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： TenantEntity.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.hasinfo.HasTenant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 多租户实体
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "多租户实体")
public abstract class TenantEntity extends Entity implements HasTenant {
  @NotBlank
  @Length(max = LengthConst.TENANT)
  @ApiModelProperty(value = "租户标识", required = true)
  private String tenant;
}
