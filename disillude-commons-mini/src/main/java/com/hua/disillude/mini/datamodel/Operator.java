/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： Operator.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.hasinfo.HasOperator;
import com.hua.disillude.mini.entity.injectable.Injectable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 操作人
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "操作人")
public class Operator implements HasOperator, Injectable {
  @Size(max = LengthConst.ID, message = "操作人标识长度不能超过" + LengthConst.ID)
  @NotBlank(message = "操作人标识不能为空")
  @ApiModelProperty(value = "操作人标识", required = true)
  private String userId;
  @Size(max = LengthConst.NAME, message = "操作人名称长度不能超过" + LengthConst.NAME)
  @NotBlank(message = "操作人名称不能为空")
  @ApiModelProperty(value = "操作人名称", required = true)
  private String userName;
  @Size(max = LengthConst.ID, message = "操作人所在组织标识长度不能超过" + LengthConst.ID)
  @NotBlank(message = "操作人所在组织标识不能为空")
  @ApiModelProperty(value = "操作人所在组织标识", required = true)
  private String orgId;
  @Size(max = LengthConst.NAME, message = "操作人所在组织名称长度不能超过" + LengthConst.NAME)
  @NotBlank(message = "操作人所在组织名称不能为空")
  @ApiModelProperty(value = "操作人所在组织名称", required = true)
  private String orgName;

  @Override
  public void inject(Object o) throws UnsupportedOperationException {
    if (o instanceof HasOperator) {
      this.setUserId(((HasOperator) o).getUserId());
      this.setUserName(((HasOperator) o).getUserName());
    }
  }
}
