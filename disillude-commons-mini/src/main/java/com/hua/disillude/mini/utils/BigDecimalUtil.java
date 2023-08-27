/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： BigDecimalUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 用于高精度处理常用的数学运算
 *
 * @author Hedh
 * @since 1.0
 */
public class BigDecimalUtil {
  private static final int DEFAULT_DIVIDE_SCALE = 10;

  /**
   * 提供精确的加法运算
   *
   * @param source
   *     被加数
   * @param value
   *     加数
   * @return 两数之和
   */
  public static double add(double source, double value) {
    BigDecimal numberSource = new BigDecimal(Double.toString(source));
    BigDecimal numberValue = new BigDecimal(Double.toString(value));
    return numberSource.add(numberValue).doubleValue();
  }

  /**
   * 提供精确的加法运算
   *
   * @param source
   *     被加数
   * @param value
   *     加数
   * @return 两数之和
   */
  public static BigDecimal add(String source, String value) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.add(numberValue);
  }

  /**
   * 提供精确的加法运算
   *
   * @param source
   *     被加数
   * @param value
   *     加数
   * @param scale
   *     精度
   * @return 两数之和
   */
  public static String add(String source, String value, @Min(value = 0) int scale) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.add(numberValue).setScale(scale, RoundingMode.HALF_UP).toString();
  }

  /**
   * 提供精确的减法运算
   *
   * @param source
   *     被减数
   * @param value
   *     减数
   * @return 两数之差
   */
  public static double subtract(double source, double value) {
    BigDecimal numberSource = new BigDecimal(Double.toString(source));
    BigDecimal numberValue = new BigDecimal(Double.toString(value));
    return numberSource.subtract(numberValue).doubleValue();
  }

  /**
   * 提供精确的减法运算
   *
   * @param source
   *     被减数
   * @param value
   *     减数
   * @return 两数之差
   */
  public static BigDecimal subtract(String source, String value) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.subtract(numberValue);
  }

  /**
   * 提供精确的减法运算
   *
   * @param source
   *     被减数
   * @param value
   *     减数
   * @param scale
   *     精度
   * @return 两数之差
   */
  public static String subtract(String source, String value, @Min(value = 0) int scale) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.subtract(numberValue).setScale(scale, RoundingMode.HALF_UP).toString();
  }

  /**
   * 提供精确的乘法运算
   *
   * @param source
   *     被乘数
   * @param value
   *     乘数
   * @return 两数之积
   */
  public static double multiply(double source, double value) {
    BigDecimal numberSource = new BigDecimal(Double.toString(source));
    BigDecimal numberValue = new BigDecimal(Double.toString(value));
    return numberSource.multiply(numberValue).doubleValue();
  }

  /**
   * 提供精确的乘法运算
   *
   * @param source
   *     被乘数
   * @param value
   *     乘数
   * @return 两数之积
   */
  public static BigDecimal multiply(String source, String value) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.multiply(numberValue);
  }

  /**
   * 提供精确的乘法运算
   *
   * @param source
   *     被乘数
   * @param value
   *     乘数
   * @param scale
   *     精度
   * @return 两数之积
   */
  public static double multiply(double source, double value, @Min(value = 0) int scale) {
    BigDecimal numberSource = new BigDecimal(Double.toString(source));
    BigDecimal numberValue = new BigDecimal(Double.toString(value));
    return round(numberSource.multiply(numberValue).doubleValue(), scale);
  }

  /**
   * 提供精确的乘法运算
   *
   * @param source
   *     被乘数
   * @param value
   *     乘数
   * @param scale
   *     精度
   * @return 两数之积
   */
  public static String multiply(String source, String value, @Min(value = 0) int scale) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.multiply(numberValue).setScale(scale, RoundingMode.HALF_UP).toString();
  }

  /**
   * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
   * 小数点以后10位，以后的数字四舍五入
   *
   * @param source
   *     被除数
   * @param value
   *     除数
   * @return 两数之商
   */

  public static double divide(double source, double value) {
    return divide(source, value, DEFAULT_DIVIDE_SCALE);
  }

  /**
   * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
   *
   * @param source
   *     被除数
   * @param value
   *     除数
   * @param scale
   *     表示表示需要精确到小数点以后几位。
   * @return 两数之商
   */
  public static double divide(double source, double value, @Min(value = 0) int scale) {
    BigDecimal numberSource = new BigDecimal(Double.toString(source));
    BigDecimal numberValue = new BigDecimal(Double.toString(value));
    return numberSource.divide(numberValue, scale, RoundingMode.HALF_UP).doubleValue();
  }

  /**
   * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
   * 定精度，以后的数字四舍五入
   *
   * @param source
   *     被除数
   * @param value
   *     除数
   * @param scale
   *     表示需要精确到小数点以后几位
   * @return 两数之商
   */
  public static String divide(String source, String value, @Min(value = 0) int scale) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.divide(numberValue, scale, RoundingMode.HALF_UP).toString();
  }

  /**
   * 提供精确的小数位四舍五入处理
   *
   * @param v
   *     需要四舍五入的数字
   * @param scale
   *     小数点后保留几位
   * @return 四舍五入后的结果
   */
  public static double round(double v, @Min(value = 0) int scale) {
    return new BigDecimal(Double.toString(v)).setScale(scale, RoundingMode.HALF_UP).doubleValue();
  }

  /**
   * 提供精确的小数位四舍五入处理
   *
   * @param target
   *     需要四舍五入的数字
   * @param scale
   *     小数点后保留几位
   * @return 四舍五入后的结果
   */
  public static String round(String target, @Min(value = 0) int scale) {
    return new BigDecimal(StringUtil.isNullOrBlank(target) ? "0" : target).setScale(scale, RoundingMode.HALF_UP).toString();
  }

  /**
   * 取余数
   *
   * @param source
   *     被除数
   * @param value
   *     除数
   * @param scale
   *     小数点后保留几位
   * @return 余数
   */
  public static String remainder(String source, String value, @Min(value = 0) int scale) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.remainder(numberValue).setScale(scale, RoundingMode.HALF_UP).toString();
  }

  /**
   * 取余数  BigDecimal
   *
   * @param source
   *     被除数
   * @param value
   *     除数
   * @param scale
   *     小数点后保留几位
   * @return 余数
   */
  public static BigDecimal remainder(BigDecimal source, BigDecimal value, @Min(value = 0) int scale) {
    return source.remainder(value).setScale(scale, RoundingMode.HALF_UP);
  }

  /**
   * 比较大小
   *
   * @param source
   *     被比较数
   * @param value
   *     比较数
   * @return 如果source > value则返回true，否则返回false
   */
  public static boolean compareTo(String source, String value) {
    BigDecimal numberSource = new BigDecimal(StringUtil.isNullOrBlank(source) ? "0" : source);
    BigDecimal numberValue = new BigDecimal(StringUtil.isNullOrBlank(value) ? "0" : source);
    return numberSource.compareTo(numberValue) > 0;
  }
}