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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Hedh
 * @since 1.0
 */
public class DateRange extends DatetimeRange implements Serializable {

  public static DateRange newInstance(Object source) {
    if (source == null) {
      return null;
    }
    DateRange target = new DateRange();
    if (source instanceof DateRange) {
      target.setStart(((DateRange) source).getStart());
      target.setEnd(((DateRange) source).getEnd());
    }
    return target;
  }

  /** 从date开始，计算months月，days日的一个区间 */
  public static DateRange newInstance(Date date, int months, int days) {
    if (date == null) {
      return null;
    }
    if (months == 0 && days == 0) {
      return null;// 无区间
    }

    DateRange r = new DateRange();
    r.setStart(date);

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (cal.get(Calendar.DAY_OF_MONTH) > 1) {
      r.setEnd(DateUtils.addDays(DateUtils.addMonths(DateUtils.addDays(date, -1), months), days));
    } else {
      r.setEnd(DateUtils.addDays(DateUtils.addMonths(date, months), days - 1));
    }
    if (r.getStart().after(r.getEnd()))// 起始日期大于截止日期是不正确的，返回空
    {
      return null;
    }
    return r;
  }

  /**
   * 指定日期当天的开始时间
   *
   * @param date
   *     日期
   * @return 开始时间
   */
  public static Date beginOfTheDate(Date date) {
    if (date == null) {
      return null;
    } else {
      return DateUtils.truncate(date, Calendar.DATE);
    }
  }

  /**
   * 指定日期当天的结束时间
   *
   * @param date
   *     日期
   * @return 结束时间
   */
  public static Date endOfTheDate(Date date) {
    if (date == null) {
      return null;
    } else {
      date = DateUtils.truncate(date, Calendar.DATE);
      date = DateUtils.addDays(date, 1);
      return DateUtils.addSeconds(date, -1);
    }
  }

  public DateRange() {
    super();
  }

  public DateRange(Date start, Date end) {
    super(beginOfTheDate(start), endOfTheDate(end));
  }

  @Override
  public void setStart(Date start) {
    super.setStart(beginOfTheDate(start));
  }

  @Override
  public void setEnd(Date end) {
    super.setEnd(endOfTheDate(end));
  }

  @Override
  public DateRange clone() {
    return new DateRange(this.getStart(), this.getEnd());
  }

  /**
   * 包含的天数
   */
  @JsonIgnore
  public int days() {
    if (this.getStart() == null || this.getEnd() == null) {
      return 0;
    } else if (this.getStart().after(this.getEnd())) {
      return -1;
    } else {
      long days = (this.getStart().getTime() - this.getEnd().getTime()) / (1000 * 60 * 60 * 24);
      return (int) days + 1;
    }
  }
}