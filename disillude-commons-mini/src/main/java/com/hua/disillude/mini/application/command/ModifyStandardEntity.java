/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： ModifyStandardEntity.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.application.command;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.dto.VersionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "编辑标准实体")
@EqualsAndHashCode(callSuper = true)
public abstract class ModifyStandardEntity extends VersionDTO {
  @Size(max = LengthConst.REMARK, message = "备注说明长度不能超过" + LengthConst.REMARK)
  @Length(max = LengthConst.REMARK)
  @ApiModelProperty(value = "备注说明")
  private String remark;
}
