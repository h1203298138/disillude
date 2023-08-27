/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： RefBill.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.Entity;
import com.hua.disillude.mini.utils.format.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * 引用单据
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "引用单据")
@EqualsAndHashCode(callSuper = true)
public class RefBill extends Entity {
  public static final int LENGTH_TYPE = 64;

  @Xss
  @Size(max = LengthConst.ID, message = "单据唯一标识长度不能超过" + LengthConst.ID)
  @ApiModelProperty(value = "单据唯一标识", required = true)
  private String id;
  @Xss
  @Size(max = LengthConst.CODE, message = "单号长度不能超过" + LengthConst.CODE)
  @ApiModelProperty(value = "单号", required = true)
  private String code;
  @Xss
  @Size(max = LengthConst.TYPE, message = "单据类型长度不能超过" + LengthConst.TYPE)
  @ApiModelProperty(value = "单据类型", required = true)
  private String type;
}
