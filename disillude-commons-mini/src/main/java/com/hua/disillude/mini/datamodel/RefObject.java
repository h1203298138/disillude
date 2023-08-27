/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： RefObject.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.ICN;
import com.hua.disillude.mini.entity.hasinfo.HasId;
import com.hua.disillude.mini.entity.injectable.Injectable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 引用对象实体
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "引用对象")
@EqualsAndHashCode(callSuper = true)
public class RefObject extends ICN implements Injectable {
  private static final long serialVersionUID = 7469083950243705013L;

  @NotBlank(message = "引用对象类型不能为空")
  @Size(max = LengthConst.TYPE, message = "引用对象类型长度不能超过" + LengthConst.TYPE)
  @ApiModelProperty(value = "引用对象类型", required = true)
  private String type;

  public static RefObject newInstance(@NotNull HasId<String> source) {
    if (source == null) {
      return null;
    }
    RefObject target = new RefObject();
    target.inject(source);
    return target;
  }

  @Override
  public void inject(Object source) {
    super.inject(source);
    this.setType(source.getClass().getSimpleName());
  }
}
