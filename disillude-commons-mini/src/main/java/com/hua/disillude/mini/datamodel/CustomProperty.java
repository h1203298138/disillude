/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： CustomProperty.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.Entity;
import com.hua.disillude.mini.utils.format.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 自定义属性对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "自定义属性")
@EqualsAndHashCode(callSuper = true)
public class CustomProperty extends Entity {
  private static final long serialVersionUID = -2440451501966526021L;

  @Xss
  @NotBlank(message = "自定义属性标签不能为空")
  @Size(max = LengthConst.NAME, message = "自定义属性标签长度不能超过" + LengthConst.NAME)
  @ApiModelProperty(value = "自定义属性标签", required = true)
  private String label;
  @Xss
  @NotBlank(message = "自定义属性/标识不能为空")
  @Size(max = LengthConst.CODE, message = "自定义属性/标识长度不能超过" + LengthConst.CODE)
  @ApiModelProperty(value = "自定义属性/标识", required = true)
  private String key;
  @Xss
  @NotBlank(message = "自定义属性值不能为空")
  @Size(max = LengthConst.REMARK, message = "自定义属性值长度不能超过" + LengthConst.REMARK)
  @ApiModelProperty(value = "自定义属性值", required = true)
  private String value;
  @Xss
  @Size(max = LengthConst.REMARK, message = "备注说明长度不能超过" + LengthConst.REMARK)
  @Length(max = LengthConst.REMARK)
  @ApiModelProperty(value = "备注说明")
  private String remark;
}