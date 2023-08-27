/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： HasName.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;


import com.hua.disillude.mini.entity.injectable.Injectable;

/**
 * 标识拥有名称的实体
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasName extends Injectable {
  /**
   * 获取名称「Name」。
   *
   * @return 「Name」
   */
  String getName();

  /**
   * 设置名称「Name」。
   *
   * @param name
   *     名称「Name」
   * @throws UnsupportedOperationException
   *     当实现类不支持修改名称时抛出。
   */
  void setName(String name) throws UnsupportedOperationException;
}
