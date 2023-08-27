/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： DateTimeFormat.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.format;

import javax.validation.constraints.NotNull;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Hedh
 * @since 1.0
 */
@SuppressWarnings("all")
public class DateTimeFormat extends SimpleDateFormat {
  public DateTimeFormat() {
    this(DEFAULT_DATE_FORMAT);
  }

  public DateTimeFormat(String dateFormat) {
    super(dateFormat);
    this.dateFormat = dateFormat;
    for (String format : DATE_FORMATS) {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      sdfs.add(sdf);
    }
  }

  /** 默认日期格式 */
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
  /** 日期匹配格式数组。 */
  public static final String[] DATE_FORMATS = new String[]{
      "yyyy-MM-dd'T'HH:mm:ss.SSSZ", DEFAULT_DATE_FORMAT, SHORT_DATE_FORMAT};
  private String dateFormat;
  private List<SimpleDateFormat> sdfs = new ArrayList<>();

  @Override
  public StringBuffer format(@NotNull Date date, @NotNull StringBuffer toAppendTo, @NotNull FieldPosition pos) {
    return super.format(date, toAppendTo, pos);
  }

  @Override
  public TimeZone getTimeZone() {
    return super.getTimeZone();
  }

  @Override
  public Date parse(@NotNull String source, @NotNull ParsePosition pos) {
    RuntimeException pe = null;
    for (SimpleDateFormat sdf : sdfs) {
      try {
        Date date = sdf.parse(source.trim(), pos);
        if (date != null) {
          return date;
        }
      } catch (RuntimeException e) {
        pe = e;
      }
    }
    if (pe == null) {
      return null;
    }
    throw pe;
  }

  @Override
  public Object clone() {
    return new DateTimeFormat(dateFormat);
  }

  /** yyyy-MM-dd日期格式校验 */
  public static boolean isFormatDate(String dt) {
    String regExpStr = "^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$";
    return dt.matches(regExpStr);
  }

  public static void main(String[] args) {
    DateTimeFormat format = new DateTimeFormat();
    System.out.println(format.format(format.getCalendar().getTime()));
  }
}