/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： DatetimeRange.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.range;

import com.hua.disillude.mini.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Hedh
 * @since 1.0
 */
@Data
@SuppressWarnings("all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "日期区间部件", description = "使用该区间会自动将endTime设置为23:59:59")
public class DatetimeRange extends Range<Date> implements Serializable {

  public DatetimeRange() {
    super();
  }

  public DatetimeRange(Date start, Date end) {
    super(start, end);
  }

  public String toFriendlyStr() {
    return toFriendlyStr("yyyy-MM-dd");
  }

  public String toFriendlyStr(String format) {
    return StringUtil.dateToString(this.getStart(), format, "") +
        "~" +
        StringUtil.dateToString(this.getEnd(), format, "");
  }

  public DatetimeRange clone() {
    return new DatetimeRange(getStart(), getEnd());
  }

  /**
   * 取得两个日期区间的交集部分。
   *
   * @param range
   *     日期区间
   * @return 交集的日期区间，如果没有交集，则返回null。
   */
  public DatetimeRange interact(DatetimeRange range) {
    if (!interactExists(range))
      return null;

    DatetimeRange dr = new DatetimeRange();
    if (getStart().before(range.getStart()))
      dr.setStart(range.getStart());
    else
      dr.setStart(getStart());
    if (getEnd().after(range.getEnd()))
      dr.setEnd(range.getEnd());
    else
      dr.setEnd(getEnd());
    return dr;
  }

  public boolean interactExists(DatetimeRange range) {
    if (getStart().after(getEnd()) || range.getStart().after(range.getEnd()))
      return false;
    if (getStart().after(range.getEnd()) || getEnd().before(range.getStart()))
      return false;
    return true;
  }

  public List<DatetimeRange> breakBy(DatetimeRange range) {
    List<DatetimeRange> ranges = new ArrayList<DatetimeRange>();
    DatetimeRange dr = interact(range);
    if (dr == null) {
      ranges.add(this);
      return ranges;
    }

    if (dr.getStart().before(getStart())) {
      Date e = DateUtils.addDays(dr.getStart(), -1);
      ranges.add(new DatetimeRange(getStart(), e));
    }
    ranges.add(dr);
    if (dr.getEnd().before(getEnd())) {
      Date e = DateUtils.addDays(dr.getEnd(), 1);
      ranges.add(new DatetimeRange(e, getEnd()));
    }
    return ranges;
  }
}