/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： TaxCalculator.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 税计算器
 *
 * @author Hedh
 * @since 1.0
 */
public class TaxCalculator {
  public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
  public static final int SCALE_MONEY = 2;

  /**
   * 根据含税金额计算税额
   *
   * @param total
   *     含税金额
   * @param taxRate
   *     税率
   * @return 税额
   */
  public static BigDecimal tax(BigDecimal total, TaxRate taxRate) {
    return tax(total, taxRate, SCALE_MONEY, ROUNDING_MODE);
  }

  /**
   * 根据含税金额计算税额
   *
   * @param total
   *     含税金额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @return 税额
   */
  public static BigDecimal tax(BigDecimal total, TaxRate taxRate, int scale) {
    return tax(total, taxRate, scale, ROUNDING_MODE);
  }

  /**
   * 根据含税金额计算税额
   *
   * @param total
   *     含税金额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @param roundingMode
   *     舍入处理
   * @return 税额
   */
  public static BigDecimal tax(BigDecimal total, TaxRate taxRate, int scale, RoundingMode roundingMode) {
    if (total == null) {
      return null;
    }
    if (taxRateNull(taxRate)) {
      return BigDecimal.ZERO;
    }

    if (taxRate.getRate().compareTo(BigDecimal.ZERO) < 0) {
      throw new RuntimeException("TaxRate < 0?");
    }

    if (TaxRate.TaxType.excludePrice.equals(taxRate.getTaxType())) {
      return total.multiply(taxRate.getRate()).divide(BigDecimal.ONE.add(taxRate.getRate()), scale,
          roundingMode);
    } else {
      return total.multiply(taxRate.getRate()).setScale(scale, roundingMode);
    }
  }

  /**
   * 根据含税金额计算去税金额
   *
   * @param total
   *     含税金额
   * @param taxRate
   *     税率
   * @return 去税金额
   */
  public static BigDecimal amount(BigDecimal total, TaxRate taxRate) {
    return amount(total, taxRate, SCALE_MONEY, ROUNDING_MODE);
  }

  /**
   * 根据含税金额计算去税金额
   *
   * @param total
   *     含税金额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @return 去税金额
   */
  public static BigDecimal amount(BigDecimal total, TaxRate taxRate, int scale) {
    return amount(total, taxRate, scale, ROUNDING_MODE);
  }

  /**
   * 根据含税金额计算去税金额
   *
   * @param total
   *     含税金额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @param roundingMode
   *     舍入处理
   * @return 去税金额
   */
  public static BigDecimal amount(BigDecimal total, TaxRate taxRate, int scale,
                                  RoundingMode roundingMode) {
    BigDecimal tax = tax(total, taxRate, scale, roundingMode);
    return total.subtract(tax);
  }

  /**
   * 根据去税金额计算含税金额
   *
   * @param amount
   *     去税金额
   * @param taxRate
   *     税率
   * @return 含税金额
   */
  public static BigDecimal total(BigDecimal amount, TaxRate taxRate) {
    return total(amount, taxRate, SCALE_MONEY, ROUNDING_MODE);
  }

  /**
   * 根据去税金额计算含税金额
   *
   * @param amount
   *     去税金额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @return 含税金额
   */
  public static BigDecimal total(BigDecimal amount, TaxRate taxRate, int scale) {
    return total(amount, taxRate, scale, ROUNDING_MODE);
  }

  /**
   * 根据去税金额计算含税金额
   *
   * @param amount
   *     去税金额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @param roundingMode
   *     舍入处理
   * @return 含税金额
   */
  public static BigDecimal total(BigDecimal amount, TaxRate taxRate, int scale, RoundingMode roundingMode) {
    if (amount == null) {
      return null;
    }
    if (taxRateNull(taxRate)) {
      return amount;
    }

    if (taxRate.getRate().compareTo(BigDecimal.ZERO) < 0) {
      throw new RuntimeException("TaxRate < 0?");
    }

    if (TaxRate.TaxType.excludePrice.equals(taxRate.getTaxType())) {
      return amount.multiply(BigDecimal.ONE.add(taxRate.getRate())).setScale(scale, roundingMode);
    } else {
      if (taxRate.getRate().compareTo(BigDecimal.ONE) > 0) {
        throw new RuntimeException("TaxRate > 1?");
      }
      return amount.divide(BigDecimal.ONE.subtract(taxRate.getRate()), scale, roundingMode);
    }
  }

  /**
   * 根据税额计算含税金额
   *
   * @param tax
   *     税额
   * @param taxRate
   *     税率
   * @return 含税金额
   */
  public static BigDecimal totalFromTax(BigDecimal tax, TaxRate taxRate) {
    return totalFromTax(tax, taxRate, SCALE_MONEY, ROUNDING_MODE);
  }

  /**
   * 根据税额计算含税金额
   *
   * @param tax
   *     税额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @return 含税金额
   */
  public static BigDecimal totalFromTax(BigDecimal tax, TaxRate taxRate, int scale) {
    return totalFromTax(tax, taxRate, scale, ROUNDING_MODE);
  }

  /**
   * 根据税额计算含税金额
   *
   * @param tax
   *     税额
   * @param taxRate
   *     税率
   * @param scale
   *     小数位数
   * @param roundingMode
   *     舍入处理
   * @return 含税金额
   */
  public static BigDecimal totalFromTax(BigDecimal tax, TaxRate taxRate, int scale, RoundingMode roundingMode) {
    if (tax == null) {
      return null;
    }
    if (taxRateNull(taxRate)) {
      return null;
    }

    if (taxRate.getRate().compareTo(BigDecimal.ZERO) <= 0) {
      throw new RuntimeException("TaxRate <= 0?");
    }

    if (TaxRate.TaxType.excludePrice.equals(taxRate.getTaxType())) {
      return tax.multiply(BigDecimal.ONE.add(taxRate.getRate())).divide(taxRate.getRate(), scale,
          roundingMode);
    } else {
      return tax.divide(taxRate.getRate(), scale, roundingMode);
    }
  }

  private static boolean taxRateNull(TaxRate taxRate) {
    return taxRate == null || taxRate.getTaxType() == null || taxRate.getRate() == null;
  }

}