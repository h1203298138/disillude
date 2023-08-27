/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： HasOperateInfo.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;

import java.util.Date;

/**
 * 标识拥有操作上下文信息
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasOperateInfo extends HasOperator {
  /**
   * 获取操作时间「Time」。
   *
   * @return 「Time」
   */
  Date getTime();

  /**
   * 设置操作时间「Time」。
   *
   * @param time
   *     操作时间「Time」
   * @throws UnsupportedOperationException
   *     当实现类不支持修改操作时间时抛出。
   */
  void setTime(Date time);
}
