/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： Range.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.range;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@AllArgsConstructor
@SuppressWarnings(value = "all")
public class Range<T extends Comparable> {
  private T start;
  private T end;

  public Range() {
    super();
  }

  /**
   * 是否包含指定的日期
   *
   * @param date
   *     日期
   * @return true=包含，false=不包含
   */
  public boolean include(T date) {
    if (date == null) {
      return false;
    } else if (getStart() != null && date.compareTo(getStart()) < 0) {
      return false;
    } else if (getEnd() != null && date.compareTo(getEnd()) > 0) {
      return false;
    } else {
      return true;
    }
  }
}