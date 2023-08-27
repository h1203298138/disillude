/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： ICN.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.hasinfo.HasCode;
import com.hua.disillude.mini.entity.hasinfo.HasICN;
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
 * 标识-编码-名称
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "标识-编码-名称")
@EqualsAndHashCode(callSuper = true)
public class ICN extends IdName implements HasICN, Injectable {
  private static final long serialVersionUID = 1158613663914097872L;

  @NotBlank(message = "编码不能为空")
  @Size(max = LengthConst.CODE, message = "编码长度不能超过" + LengthConst.CODE)
  @ApiModelProperty(value = "编码", required = true)
  private String code;

  public ICN(String id) {
    this.setId(id);
  }

  public ICN(String id, String code, String name) {
    this.setId(id);
    this.setName(name);
    this.code = code;
  }

  public static ICN newInstance(Object source) {
    if (source == null) {
      return null;
    }
    ICN target = new ICN();
    target.inject(source);
    return target;
  }

  @Override
  public void inject(Object source) {
    if (source == null) {
      return;
    }
    super.inject(source);
    if (source instanceof HasCode) {
      this.setCode(((HasCode) source).getCode());
    }
  }
}