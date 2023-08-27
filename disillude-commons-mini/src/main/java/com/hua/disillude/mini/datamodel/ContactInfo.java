/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： ContactInfo.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.utils.format.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * 联系信息对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "联系信息")
public class ContactInfo {
  @Xss
  @Size(max = LengthConst.NAME, message = "联系人长度不能超过" + LengthConst.NAME)
  @ApiModelProperty(value = "联系人")
  private String contactPerson;
  @Xss
  @Size(max = LengthConst.TELEPHONE, message = "联系电话长度不能超过" + LengthConst.TELEPHONE)
  @ApiModelProperty(value = "联系电话")
  private String telephone;
  @Xss
  @Size(max = LengthConst.EMAIL, message = "电子邮箱长度不能超过" + LengthConst.EMAIL)
  @ApiModelProperty(value = "电子邮箱")
  private String email;
}