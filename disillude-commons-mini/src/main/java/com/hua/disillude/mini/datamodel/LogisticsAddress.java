/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： LogisticsAddress.java
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * 物流地址对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "物流地址")
@EqualsAndHashCode(callSuper = true)
public class LogisticsAddress extends Address {
  @Xss
  @Size(max = LengthConst.NAME, message = "地址标题长度不能超过" + LengthConst.NAME)
  @ApiModelProperty("地址标题")
  private String caption;
  @ApiModelProperty("默认")
  private boolean asDefault = false;
  @Xss
  @Size(max = LENGTH_DETAIL, message = "联系人长度不能超过" + LENGTH_DETAIL)
  @ApiModelProperty("联系人")
  private String contactPerson;
  @Xss
  @Size(max = LENGTH_DETAIL, message = "联系电话长度不能超过" + LENGTH_DETAIL)
  @ApiModelProperty("联系电话")
  private String contactPhone;
}
