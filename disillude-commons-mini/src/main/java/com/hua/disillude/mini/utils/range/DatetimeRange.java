/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： DateTimeRange.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.range;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Hedh
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("all")
public class DatetimeRange extends Range<Date> {
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public DatetimeRange(Date start, Date end) {
    super(start, end);
  }

  public DatetimeRange(Date start, Date end, boolean autoCorrect) {
    super(start, end, autoCorrect);
  }

  public static void main(String[] args) throws ParseException {
    // 是否包含某个时刻
    SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    // DatetimeRange range = new DatetimeRange(
    //         sdf.parse("2022-04-04 00:00:00"),
    //         sdf.parse("2022-04-14 23:59:59")
    // );
    // System.out.println(sdf.format(range.getStart()) + " - " + sdf.format(range.getEnd()));
    // System.out.println(sdf.format(new Date(System.currentTimeMillis())));
    // boolean include = range.include(new Date(System.currentTimeMillis()));
    // System.out.println(include);
    // 两个Datetime范围是否有交集
    DatetimeRange range = new DatetimeRange(
        sdf.parse("2022-04-04 00:00:00"),
        sdf.parse("2022-04-14 23:59:59")
    );
    DatetimeRange result = range.interact(new DatetimeRange(null, null));
    System.out.println(sdf.format(result.getStart()) + " - " + sdf.format(result.getEnd()));
  }

  /** 判断是否有交集 */
  public boolean interactExists(DatetimeRange range) {
    // 起始时间大于截止时间时将两值交换，使其为正常范围
    if (this.getStart() != null && this.getEnd() != null && this.getStart().after(this.getEnd())) {
      Date o = this.getStart();
      this.setStart(this.getEnd());
      this.setEnd(o);
    }
    if (range.getStart() != null && range.getEnd() != null) {
      Date o = range.getStart();
      range.setStart(range.getEnd());
      range.setEnd(o);
    }
    return false;
  }

  /** 取交集 */
  public DatetimeRange interact(DatetimeRange range) {
    // TODO
    if (this.getStart() == null && this.getEnd() == null) {
      return range;
    }
    if (range.getStart() == null && range.getEnd() == null) {
      return this;
    }
    return null;
  }

  @Override
  public DatetimeRange clone() {
    return new DatetimeRange(this.getStart(), this.getEnd());
  }

}