/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： IdCode.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.hasinfo.HasCode;
import com.hua.disillude.mini.entity.hasinfo.HasId;
import com.hua.disillude.mini.entity.injectable.Injectable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 标识-单号
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "标识-单号")
@EqualsAndHashCode(callSuper = true)
public class IdCode extends IdentifiedDomainObject implements HasId<String>, HasCode, Injectable {
  private static final long serialVersionUID = -7597241575269953963L;

  @NotBlank(message = "单号不能为空")
  @Size(max = LengthConst.CODE, message = "单号长度不能超过" + LengthConst.CODE)
  @ApiModelProperty(value = "单号")
  private String code;

  @Override
  @SuppressWarnings("unchecked")
  public void inject(Object source) throws UnsupportedOperationException {
    if (source == null) {
      return;
    }
    if (source instanceof HasId) {
      this.setId(((HasId<String>) source).getId());
    }
    if (source instanceof HasCode) {
      this.setCode(((HasCode) source).getCode());
    }
  }
}