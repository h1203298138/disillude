/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： HasBizState.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.business;

/**
 * 标识拥有业务状态的实体
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasBizState {
  /**
   * 获取业务状态「bizState」。
   *
   * @return 「bizState」
   */
  String getBizState();

  /**
   * 设置业务状态「bizState」。
   *
   * @param bizState
   *     业务状态「bizState」
   * @throws UnsupportedOperationException
   *     当实现类不支持修改业务状态时抛出。
   */
  void setBizState(String bizState);
}
