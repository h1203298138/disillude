/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： HasErrorCode.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.exception;

/**
 * @author Hedh
 * @since 1.0
 */
public interface HasErrorCode {
  /**
   * 获取错误码「code」。
   *
   * @return 「LastModifiedInfo」
   */
  int getCode();
}
