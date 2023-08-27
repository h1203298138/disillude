/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
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
  /** 开始 */
  private T start;
  /** 截止 */
  private T end;
  /** 自动矫正开始截止值 */
  private boolean autoCorrect = false;

  public Range() {
    super();
  }

  public Range(T start, T end) {
    this.start = start;
    this.end = end;
  }

  public boolean include(T date) {
    if (date == null || (this.start == null && this.end == null)) {
      return false;
    } else if (this.start == null && this.end != null) {
      return date.compareTo(this.end) < 0;
    } else if (this.start != null && this.end == null) {
      return date.compareTo(this.start) > 0;
    } else if (this.start != null) {
      if (this.autoCorrect == true && this.start.compareTo(this.end) > 0) {
        final T o = this.start;
        this.start = this.end;
        this.end = o;
      }
      return date.compareTo(this.start) >= 0 && date.compareTo(this.end) <= 0;
    } else {
      return true;
    }
  }
}