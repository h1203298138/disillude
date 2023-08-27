/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： IdName.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.hasinfo.HasId;
import com.hua.disillude.mini.entity.hasinfo.HasName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 标识-名称
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "标识-名称")
@EqualsAndHashCode(callSuper = true)
public class IdName extends IdentifiedDomainObject implements HasId<String>, HasName {
  private static final long serialVersionUID = -5557035889844751352L;

  @NotBlank(message = "名称不能为空")
  @Size(max = LengthConst.NAME, message = "名称长度不能超过" + LengthConst.NAME)
  @ApiModelProperty(value = "名称")
  private String name;

  public IdName(String id, String name) {
    this.setId(id);
    this.name = name;
  }

  @Override
  @SuppressWarnings(value = "unchecked")
  public void inject(Object source) throws UnsupportedOperationException {
    if (source == null) {
      return;
    }
    if (source instanceof HasId) {
      this.setId(((HasId<String>) source).getId());
    }
    if (source instanceof HasName) {
      this.setName(((HasName) source).getName());
    }
  }
}