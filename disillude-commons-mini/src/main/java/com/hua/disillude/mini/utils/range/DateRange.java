/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： DateRange.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.range;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Hedh
 * @since 1.0
 */
public class DateRange extends DatetimeRange {
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

  public DateRange(Date start, Date end) {
    super(start, end);
  }

  public DateRange(Date start, Date end, boolean autoCorrect) {
    super(start, end, autoCorrect);
  }

  public static void main(String[] args) throws ParseException {
    // 是否包含某个时刻
    SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    DateRange range = new DateRange(sdf.parse("2022-04-04"), sdf.parse("2022-04-14"));
    System.out.println(sdf.format(range.getStart()) + " - " + sdf.format(range.getEnd()));
    System.out.println(sdf.format(new Date(System.currentTimeMillis())));
    boolean include = range.include(new Date(System.currentTimeMillis()));
    System.out.println(include);
  }

  /** 判断是否有交集 */
  public boolean interactExists(DateRange range) {
    // TODO
    return false;
  }

  /** 取交集 */
  public DateRange interact(DateRange range) {
    // TODO
    return null;
  }

  @Override
  public DateRange clone() {
    return new DateRange(this.getStart(), this.getEnd());
  }
}