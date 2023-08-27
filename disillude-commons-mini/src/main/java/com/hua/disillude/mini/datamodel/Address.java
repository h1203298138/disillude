/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： Address.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.entity.IdName;
import com.hua.disillude.mini.utils.format.postcode.ValidPostcode;
import com.hua.disillude.mini.utils.format.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * 地址信息对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "地址信息")
public class Address {
  /** 省级行政区名称字段长度定义 */
  public static final int LENGTH_PROVINCE = 30;
  /** 地级行政区字段长度定义 */
  public static final int LENGTH_CITY = 100;
  /** 县/区字段长度定义 */
  public static final int LENGTH_COUNTY = 40;
  /** 详细信息字段长度定义 */
  public static final int LENGTH_DETAIL = 200;
  /** 邮政编码字段长度定义 */
  public static final int LENGTH_POSTCODE = 25;

  @ApiModelProperty(value = "省级行政区名称")
  private IdName province;
  @ApiModelProperty(value = "地级行政区名称")
  private IdName city;
  @ApiModelProperty(value = "县/区级行政区名称")
  private IdName county;
  @Xss
  @Size(max = LENGTH_DETAIL, message = "详细地址长度不能超过" + LENGTH_DETAIL)
  @ApiModelProperty(value = "详细地址")
  private String detail;
  @Xss
  @Size(max = LENGTH_POSTCODE, message = "邮政编码长度不能超过" + LENGTH_POSTCODE)
  @ValidPostcode(message = "请输入正确的邮政编码")
  @ApiModelProperty(value = "邮政编码")
  private String postcode;
}