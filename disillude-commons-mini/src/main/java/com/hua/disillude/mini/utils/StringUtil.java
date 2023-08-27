/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： StringUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * 与String相关的工具集，并且其中所有的方法都是null安全的。
 * <p>
 * <p>
 * 提供了一组简化了的各种常见数据类型与字符串之间互相转换的功能。因为是简化，调用者若要使用更完整的功能，推荐使用Apache
 * Commons中的lang3组件。
 * <p>
 *
 * @author Hedh
 * @since 1.0
 */
public class StringUtil {

  /**
   * 指定字符串是否空或空字符串。
   *
   * @see #hasLength(String)
   */
  public static boolean isNullOrEmpty(String str) {
    return str == null || "".equals(str);
  }

  /**
   * 指定字符串是否是空或空白字符串。
   *
   * @see #hasText(String)
   */
  public static boolean isNullOrBlank(String str) {
    return str == null || "".equals(str.trim());
  }

  /**
   * 判断指定字符串是否具有长度，即不为null或空字符串。
   *
   * @return 返回true表示具有长度，返回false则反之。
   * @see #isNullOrEmpty(String)
   * @since 1.22.0
   */
  public static boolean hasLength(String str) {
    return str != null && str.length() > 0;
  }

  /**
   * 判断指定字符串是否含有文本，即不为null或空白字符串。
   *
   * @return 返回true表示含有文本，返回false则反之。
   * @see #isNullOrBlank(String)
   * @since 1.22.0
   */
  public static boolean hasText(String str) {
    return str != null && !"".equals(str.trim());
  }

  /** 整数转换时使用的基数，表示2进制。 */
  public static final int RADIX_BIN = 2;
  /** 整数转换时使用的基数，表示8进制。 */
  public static final int RADIX_OCT = 8;
  /** 整数转换时使用的基数，表示10进制。 */
  public static final int RADIX_DEC = 10;
  /** 整数转换时使用的基数，表示16进制。 */
  public static final int RADIX_HEX = 16;
  /** 用于数字千位分隔符的逗号。 */
  private static final String COMMA = ",";

  /**
   * 将字符串转换为Integer类型。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回null。
   */
  public static Integer toInteger(String str) throws NumberFormatException {
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    } else {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Integer.valueOf(s);
    }
  }

  /**
   * 将字符串转换为Integer类型。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  public static Integer toInteger(String str, Integer defaultValue) {
    try {
      if (StringUtil.isNullOrBlank(str)) {
        return defaultValue;
      }
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Integer.valueOf(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为Integer类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回null。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   */
  public static Integer toInteger(String str, int radix) throws NumberFormatException {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    } else {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Integer.valueOf(s, radix);
    }
  }

  /**
   * 将字符串转换为Integer类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回defaultValue。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  public static Integer toInteger(String str, int radix, Integer defaultValue) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    try {
      if (StringUtil.isNullOrBlank(str)) {
        return defaultValue;
      } else {
        String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
        return Integer.valueOf(s, radix);
      }
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为int类型。
   *
   * @param str
   *     字符串。
   * @throws NumberFormatException
   *     当字符串不能被转换为数值。
   */
  @SuppressWarnings("ConstantConditions")
  public static int toPrimitiveInt(String str) throws NumberFormatException {
    String s = str;
    if (s != null) {
      s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    }
    return Integer.parseInt(s);
  }

  /**
   * 将字符串转换为int类型。
   *
   * @param str
   *     字符串。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  @SuppressWarnings("ConstantConditions")
  public static int toPrimitiveInt(String str, int defaultValue) {
    try {
      String s = str;
      if (s != null) {
        s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      }
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为int类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @throws NumberFormatException
   *     当字符串不能被转换为数值。
   */
  @SuppressWarnings("ConstantConditions")
  public static int toPrimitiveInt2(String str, int radix) throws NumberFormatException {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    String s = str;
    if (s != null) {
      s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    }
    return Integer.parseInt(s, radix);
  }

  /**
   * 将字符串转换为int类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回defaultValue。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  public static int toPrimitiveInt(String str, int radix, int defaultValue) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    try {
      if (StringUtil.isNullOrBlank(str)) {
        return defaultValue;
      } else {
        String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
        return Integer.parseInt(s, radix);
      }
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为Long类型。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回null。
   */
  public static Long toLong(String str) throws NumberFormatException {
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    } else {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Long.valueOf(s);
    }
  }

  /**
   * 将字符串转换为Long类型。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回null。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  public static Long toLong(String str, Long defaultValue) {
    try {
      if (StringUtil.isNullOrBlank(str)) {
        return defaultValue;
      }
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Long.valueOf(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为Integer类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回null。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @throws IllegalArgumentException
   *     当传入的参数radix非法。
   */
  public static Long toLong(String str, int radix) throws NumberFormatException {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    } else {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Long.valueOf(s, radix);
    }
  }

  /**
   * 将字符串转换为Long类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回defaultValue。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  public static Long toLong(String str, int radix, Long defaultValue) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    try {
      if (StringUtil.isNullOrBlank(str)) {
        return defaultValue;
      } else {
        String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
        return Long.valueOf(s, radix);
      }
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为long类型。
   *
   * @param str
   *     字符串。
   */
  @SuppressWarnings("ConstantConditions")
  public static long toPrimitiveLong(String str) throws NumberFormatException {
    String s = str;
    if (s != null) {
      s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    }
    return Long.parseLong(s);
  }

  /**
   * 将字符串转换为long类型。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  public static long toPrimitiveLong(String str, long defaultValue) {
    try {
      if (StringUtil.isNullOrBlank(str)) {
        return defaultValue;
      }
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Long.parseLong(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为long类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   */
  @SuppressWarnings("ConstantConditions")
  public static long toPrimitiveLong(String str, int radix) throws NumberFormatException {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    String s = str;
    if (s != null) {
      s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    }
    return Long.parseLong(s, radix);
  }

  /**
   * 将字符串转换为long类型。支持各种进制的转换。
   *
   * @param str
   *     字符串。传入null、空字符串以及空白字符串，将导致返回defaultValue。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @param defaultValue
   *     默认取值，即当参数str为null，或无法正常转换为整数时，即将返回这个值。
   */
  public static long toPrimitiveLong(String str, int radix, long defaultValue) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    try {
      if (StringUtil.isNullOrBlank(str)) {
        return defaultValue;
      } else {
        String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
        return Long.parseLong(s, radix);
      }
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /** 内置布尔值为真时的字符串数组。 */
  public static final String[] TRUE_STRINGS = new String[]{
      "true", "t", "1", "on", "yes", "y", "是"};
  /** 内置布尔值为假时的字符串数组。 */
  public static final String[] FALSE_STRINGS = new String[]{
      "false", "f", "0", "off", "no", "n", "否", "不", "非"};

  /**
   * 将字符串转换为Boolean类型。
   * <p>
   * <p>
   * 此方法将尝试精确匹配字符串。只有当字符串去掉前后空格后为{@link #TRUE_STRINGS}
   * 中之一时（大小写不敏感），返回Boolean.TRUE； 只有当字符串去掉前后空格后为{@link #FALSE_STRINGS}
   * 中之一时（大小写不敏感），返回Boolean.FALSE；其它情况都将返回null。
   *
   * @param str
   *     字符串。
   */
  public static Boolean toBoolean(String str) {
    if (str == null) {
      return null;
    }
    str = str.trim().toLowerCase();
    for (String s : StringUtil.TRUE_STRINGS) {
      if (s.equals(str)) {
        return Boolean.TRUE;
      }
    }
    for (String s : StringUtil.FALSE_STRINGS) {
      if (s.equals(str)) {
        return Boolean.FALSE;
      }
    }
    return null;
  }

  /**
   * 将字符串转换为Boolean类型。
   *
   * @param str
   *     字符串。
   * @param defaultValue
   *     默认取值。当传入true时，将严格检查字符串是否为{@link #FALSE_STRINGS}
   *     中之一（大小写不敏感），其它字符串包括null 将被返回Boolean.TRUE；当传入false时反之。
   */
  public static Boolean toBoolean(String str, boolean defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    str = str.trim().toLowerCase();
    if (defaultValue) {
      for (String s : StringUtil.FALSE_STRINGS) {
        if (s.equals(str)) {
          return Boolean.FALSE;
        }
      }
      return Boolean.TRUE;
    } else {
      for (String s : StringUtil.TRUE_STRINGS) {
        if (s.equals(str)) {
          return Boolean.TRUE;
        }
      }
      return Boolean.FALSE;
    }
  }

  /**
   * 将字符串转换为boolean类型。
   *
   * @param str
   *     字符串。
   * @param defaultValue
   *     默认取值。当传入true时，将严格检查字符串是否为{@link #FALSE_STRINGS}
   *     中之一（大小写不敏感），其它字符串包括null 将被返回true；当传入false时反之。
   */
  public static boolean toPrimitiveBoolean(String str, boolean defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    str = str.trim().toLowerCase();
    if (defaultValue) {
      for (String s : StringUtil.FALSE_STRINGS) {
        if (s.equals(str)) {
          return false;
        }
      }
      return true;
    } else {
      for (String s : StringUtil.TRUE_STRINGS) {
        if (s.equals(str)) {
          return true;
        }
      }
      return false;
    }
  }

  /**
   * 将字符串转换为Float类型。
   */
  public static Float toFloat(String str) throws NumberFormatException {
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    }
    String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    return Float.valueOf(s);
  }

  /**
   * 将字符串转换为Float类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值。
   */
  public static Float toFloat(String str, Float defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    try {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Float.valueOf(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为float类型。
   *
   * @param str
   *     字符串。
   */
  public static float toPrimitiveFloat(String str) throws NumberFormatException {
    // Float.parseFloat，传入null抛出的是NullPointerException，与Integer.parseInt()不一致，这里将进行统一处理。
    if (str == null) {
      throw new NumberFormatException();
    }
    String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    return Float.parseFloat(s);
  }

  /**
   * 将字符串转换为float类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值。
   */
  public static float toPrimitiveFloat(String str, float defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    try {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Float.parseFloat(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为Double类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回null。
   */
  public static Double toDouble(String str) throws NumberFormatException {
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    }
    String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    return Double.valueOf(s);
  }

  /**
   * 将字符串转换为Double类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值。
   */
  public static Double toDouble(String str, Double defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    try {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Double.valueOf(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为double类型。
   *
   * @param str
   *     字符串。
   */
  public static double toPrimitiveDouble(String str) throws NumberFormatException {
    // Double.parseFloat，传入null抛出的是NullPointerException，与Integer.parseInt()不一致，这里将进行统一处理。
    if (str == null) {
      throw new NumberFormatException();
    }
    String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    return Double.parseDouble(s);
  }

  /**
   * 将字符串转换为double类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值。
   */
  public static double toPrimitiveDouble(String str, double defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    try {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return Double.parseDouble(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * 将字符串转换为BigDecimal类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回null。
   */
  public static BigDecimal toBigDecimal(String str) throws NumberFormatException {
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    }
    String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
    return new BigDecimal(s);
  }

  /**
   * 将字符串转换为BigDecimal类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值。
   */
  public static BigDecimal toBigDecimal(String str, BigDecimal defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    try {
      String s = StringUtils.replace(str.trim(), StringUtil.COMMA, "");
      return new BigDecimal(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /** 日期匹配格式数组。 */
  public static final String[] DATE_FORMATS = new String[]{
      "yyyy-M-d HH:mm:ss.SSS", "yyyy-M-d HH:mm:ss", "yyyy-M-d HH:mm", "yyyy-M-d HH", "yyyy-M-d"};
  /** 默认日期格式模板。 */
  public static final String DEFAULT_DATE_FORMAT = "yyyy-M-d HH:mm:ss.SSS";

  /**
   * 将字符串转换为Date类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回null。
   * @param formats
   *     日期格式模板数组，传入null将被忽略，不指定任何格式则将试图自动匹配（推荐），相关语法参见java.text.
   *     SimpleDateFormat
   *     。将依次尝试使用传入的日期格式模板进行转换，对于同一个传入字符串如果有多个格式模板能够转换成功，将始终返回第一个成功转换的结果
   *     。但这时的返回这可能并不是期望的结果，例如如下调用：
   *
   *     <pre>
   *                                                                                      <code>StringUtil.toDate("2011-12-23 10:40:23", "yyyy-M-d", "yyyy-M-d HH:mm:ss")</code>
   *                                                                                      </pre>
   *     <p>
   *     上述调用的返回结果将是2011年12月23日，时间部分被忽略。原因是先尝试匹配了格式模板“yyyy-M-d”，
   *     传入字符串能够被正确解析，结果被返回。因此正确的调用应该被修改为：
   *
   *     <pre>
   *                                                                                      <code>StringUtil.toDate("2011-12-23 10:40:23", "yyyy-M-d HH:mm:ss", "yyyy-M-d")</code>
   *                                                                                      </pre>
   * @throws IllegalArgumentException
   *     当传入参数format非法。
   */
  public static Date toDate(String str, String... formats)
      throws IllegalArgumentException, ParseException {
    if (StringUtil.isNullOrBlank(str)) {
      return null;
    }

    String[] ripeFormats = formats.length > 0 ? formats : StringUtil.DATE_FORMATS;
    List<SimpleDateFormat> sdfs = new ArrayList<SimpleDateFormat>();
    for (String format : ripeFormats) {
      if (format == null) {
        continue;
      }
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      sdfs.add(sdf);
    }

    ParseException pe = null;
    for (SimpleDateFormat sdf : sdfs) {
      try {
        return sdf.parse(str.trim());
      } catch (ParseException e) {
        pe = e;
      }
    }
    // noinspection ConstantConditions
    throw pe;
  }

  /**
   * 将字符串转换为Date类型。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回defaultValue。
   * @param defaultValue
   *     默认取值。
   * @param formats
   *     日期格式模板数组，传入null将被忽略，不指定任何格式则将试图自动匹配（推荐），相关语法参见java.text.
   *     SimpleDateFormat
   *     。将依次尝试使用传入的日期格式模板进行转换，对于同一个传入字符串如果有多个格式模板能够转换成功，将始终返回第一个成功转换的结果
   *     。关于此参数更多的说明请参见{@link #toDate(String, String...)}。
   * @throws IllegalArgumentException
   *     当传入参数format非法。
   */
  public static Date toDate(String str, Date defaultValue, String... formats)
      throws IllegalArgumentException {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }

    String[] ripeFormats = formats.length > 0 ? formats : StringUtil.DATE_FORMATS;
    List<SimpleDateFormat> sdfs = new ArrayList<SimpleDateFormat>();
    for (String format : ripeFormats) {
      if (format == null) {
        continue;
      }
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      sdfs.add(sdf);
    }

    for (SimpleDateFormat sdf : sdfs) {
      try {
        return sdf.parse(str.trim());
      } catch (ParseException e) {
        // Do Nothing
      }
    }
    return defaultValue;
  }

  /**
   * 将字符串转换为指定的枚举类型。转换规则为按照枚举类型name()进行字符串匹配，大小写不敏感。等价于调用：<br>
   * <code>toEnum(str, enumClass, null)</code>
   *
   * @see #toEnum(String, Class, Enum)
   */
  public static <T extends Enum> T toEnum(String str, Class<T> enumClass) {
    return StringUtil.toEnum(str, enumClass, null);
  }

  /**
   * 将字符串转换为指定的枚举类型。转换规则为按照枚举类型name()进行字符串匹配，大小写不敏感。
   *
   * @param str
   *     字符串。当传入null、空字符串或空白字符串，将导致返回defaultValue。
   * @param enumClass
   *     指定枚举类类型。not null。
   * @param defaultValue
   *     默认取值。
   */
  public static <T extends Enum> T toEnum(String str, Class<T> enumClass, T defaultValue) {
    if (StringUtil.isNullOrBlank(str)) {
      return defaultValue;
    }
    str = str.trim();
    for (Object value : EnumSet.allOf(enumClass)) {
      if (((T) value).name().equalsIgnoreCase(str)) {
        return (T) value;
      }
    }
    return defaultValue;
  }

  /**
   * 将对象转换为字符串输出。
   *
   * @param value
   *     值对象，传出null将返回null。
   */
  public static String toString(Object value) {
    return StringUtil.toString(value, null);
  }

  /**
   * 将对象转换为字符串输出。
   *
   * @param value
   *     值对象。默认情况下将返回对象的toString()的返回值，以下是一些特殊情况：
   *     <ul>
   *     <li>当对象是Date时，等价于调用：<br>
   *     <code>dateToString((Date) value, DEFAULT_DATE_FORMAT, nullString)</code>
   *     </li>
   *     <li>当对象是Enum时，始终使用的是name()方法返回的字符串。</li>
   *     </ul>
   * @param nullString
   *     指定当value为null时返回的字符串。
   */
  public static String toString(Object value, String nullString) {
    if (value == null) {
      return nullString;
    }
    if (value instanceof Date) {
      return StringUtil.dateToString((Date) value, StringUtil.DEFAULT_DATE_FORMAT, nullString);
    } else if (value instanceof Enum) {
      return ((Enum) value).name();
    } else {
      return value.toString();
    }
  }

  /**
   * 将int转换为字符串。
   */
  public static String toString(int value) {
    return String.valueOf(value);
  }

  /**
   * 将int转换为字符串。
   *
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   */
  public static String toString(int value, int radix) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    if (radix == StringUtil.RADIX_BIN) {
      return Integer.toBinaryString(value);
    } else if (radix == StringUtil.RADIX_OCT) {
      return Integer.toOctalString(value);
    } else if (radix == StringUtil.RADIX_HEX) {
      return Integer.toHexString(value);
    } else if (radix == StringUtil.RADIX_DEC) {
      return Integer.toString(value);
    } else {
      return Integer.toString(value, radix);
    }
  }

  /**
   * 将long转换为字符串。
   */
  public static String toString(long value) {
    return String.valueOf(value);
  }

  /**
   * 将long转换为字符串。
   *
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   */
  public static String toString(long value, int radix) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    if (radix == StringUtil.RADIX_BIN) {
      return Long.toBinaryString(value);
    } else if (radix == StringUtil.RADIX_OCT) {
      return Long.toOctalString(value);
    } else if (radix == StringUtil.RADIX_HEX) {
      return Long.toHexString(value);
    } else if (radix == StringUtil.RADIX_DEC) {
      return Long.toString(value);
    } else {
      return Long.toString(value, radix);
    }
  }

  /**
   * 将boolean转换为字符串。
   *
   * @param value
   *     当传入true时，将返回"true"；当传入false时，将返回"false"。
   */
  public static String toString(boolean value) {
    if (value) {
      return "true";
    } else {
      return "false";
    }
  }

  /**
   * 将float转换为字符串。
   */
  public static String toString(float value) {
    return String.valueOf(value);
  }

  /**
   * 将double转换为字符串。
   */
  public static String toString(double value) {
    return String.valueOf(value);
  }

  /**
   * 将Integer类型对象转换为字符串。等价于调用:<br>
   * <code>integerToString(value, radix, null);</code>
   *
   * @see #integerToString(Integer, int, String)
   * @see #toString(Object)
   */
  public static String integerToString(Integer value, int radix) {
    return StringUtil.integerToString(value, radix, null);
  }

  /**
   * 将Integer类型对象转换为字符串。
   *
   * @param value
   *     值对象。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @param nullString
   *     指定当value为null时返回的字符串。
   * @see #toString(Object)
   */
  public static String integerToString(Integer value, int radix, String nullString) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    if (value == null) {
      return nullString;
    }
    if (radix == StringUtil.RADIX_BIN) {
      return Integer.toBinaryString(value);
    } else if (radix == StringUtil.RADIX_OCT) {
      return Integer.toOctalString(value);
    } else if (radix == StringUtil.RADIX_HEX) {
      return Integer.toHexString(value);
    } else if (radix == StringUtil.RADIX_DEC) {
      return value.toString();
    } else {
      return Integer.toString(value, radix);
    }
  }

  /**
   * 将Long类型对象转换为字符串。等价于调用:<br>
   * <code>longToString(value, radix, null);</code>
   *
   * @see #longToString(Long, int, String)
   */
  public static String longToString(Long value, int radix) {
    return StringUtil.longToString(value, radix, null);
  }

  /**
   * 将Long类型对象转换为字符串。
   *
   * @param value
   *     值对象。
   * @param radix
   *     指定转换基数，例如传入{@link #RADIX_HEX}表示按照16进制数字解释字符串。通常情况调用者应该控制其为大于等于 java
   *     .lang.Character.MIN_RADIX且小于等于java.lang.Character.MAX_RADIX的整数，
   *     若超出这个范围将被解释为 {@link #RADIX_DEC}。
   * @param nullString
   *     指定当value为null时返回的字符串。
   */
  public static String longToString(Long value, int radix, String nullString) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      radix = StringUtil.RADIX_DEC;
    }
    if (value == null) {
      return nullString;
    }
    if (radix == StringUtil.RADIX_BIN) {
      return Long.toBinaryString(value);
    } else if (radix == StringUtil.RADIX_OCT) {
      return Long.toOctalString(value);
    } else if (radix == StringUtil.RADIX_HEX) {
      return Long.toHexString(value);
    } else if (radix == StringUtil.RADIX_DEC) {
      return value.toString();
    } else {
      return Long.toString(value, radix);
    }
  }

  /**
   * 将Date类型对象转换为字符串。等价于调用：<br>
   * <code>dateToString(value, format, null);</code>
   *
   * @see #dateToString(Date, String, String)
   */
  public static String dateToString(Date value, String format) throws IllegalArgumentException {
    return StringUtil.dateToString(value, format, null);
  }

  /**
   * 将Date类型对象转换为字符串。
   *
   * @param value
   *     值对象。
   * @param format
   *     日期格式模板，传入null等价于默认格式{@link #DEFAULT_DATE_FORMAT}
   *     ，相关语法参见java.text.SimpleDateFormat。
   * @param nullString
   *     指定当value为null时返回的字符串。
   */
  public static String dateToString(Date value, String format, String nullString) throws IllegalArgumentException {
    if (value == null) {
      return nullString;
    }
    if (format == null) {
      format = StringUtil.DEFAULT_DATE_FORMAT;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(value);
  }

  /**
   * 判断指定字符串中是否包含有多字节字符，例如汉字。
   */
  public static boolean containsMultiBytesChar(String str) {
    if (str == null) {
      return false;
    } else {
      return str.getBytes().length != str.length();
    }
  }

  /**
   * 判断指定字符串中是否包含有汉字。这里仅包含了一个近似的汉字判断算法，等价于调用：<br>
   * <code>containsMultiBytesChar(str);</code>
   *
   * @see #containsMultiBytesChar(String)
   */
  public static boolean containsChinese(String str) {
    return StringUtil.containsMultiBytesChar(str);
  }

  @SuppressWarnings("unchecked")
  public static <T> T cast(Object obj) {
    return (T) obj;
  }
}