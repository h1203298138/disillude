/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： Corporation.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.entity.IdName;
import com.hua.disillude.mini.utils.format.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司信息对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "公司信息")
public class Corporation {
  @Xss
  @ApiModelProperty(value = "法人")
  private String legalPerson;
  @ApiModelProperty(value = "行业")
  private IdName profession;
  @ApiModelProperty(value = "成立时间")
  private Date establishDate;
  @ApiModelProperty(value = "注册资金")
  private BigDecimal registerCapital;
  @ApiModelProperty(value = "注册地址")
  private Address registerAddress;
}