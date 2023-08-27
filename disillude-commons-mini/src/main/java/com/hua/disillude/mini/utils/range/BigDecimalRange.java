/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： BigDecimalRange.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.range;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BigDecimalRange extends Range<BigDecimal> {
  public BigDecimalRange(BigDecimal start, BigDecimal end) {
    super(start, end);
  }

  public BigDecimalRange(BigDecimal start, BigDecimal end, boolean autoCorrect) {
    super(start, end, autoCorrect);
  }

  public static void main(String[] args) {
    BigDecimalRange autoCorrectFalse = new BigDecimalRange(BigDecimal.ZERO, BigDecimal.ONE);
    System.out.println(autoCorrectFalse.include(BigDecimal.ZERO));
    BigDecimalRange autoCorrectTrue = new BigDecimalRange(BigDecimal.ONE, BigDecimal.ZERO, true);
    System.out.println(autoCorrectTrue.include(BigDecimal.ZERO));
  }
}