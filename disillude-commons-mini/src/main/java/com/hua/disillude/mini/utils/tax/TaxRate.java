/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： TaxRate.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.tax;

import com.hua.disillude.mini.constant.LengthConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "税率")
public class TaxRate {
  public enum TaxType {
    /** 价外税 */
    @ApiModelProperty(value = "价外税", notes = "税款=（含税价格/（1+税率））*税率=不含税价格*税率")
    excludePrice,
    /** 价内税 */
    @ApiModelProperty(value = "价内税", notes = "税款=含税价格*税率")
    includePrice,
  }

  @NotNull
  @ApiModelProperty("税类型")
  private TaxType taxType = TaxType.includePrice;
  @NotNull
  @Min(0)
  @ApiModelProperty("税率")
  private BigDecimal rate = BigDecimal.ZERO;
  @Length(max = LengthConst.NAME)
  @ApiModelProperty("税名称")
  private String name;

  public TaxRate(TaxType taxType, BigDecimal rate) {
    this.taxType = taxType;
    this.rate = rate;
  }
}