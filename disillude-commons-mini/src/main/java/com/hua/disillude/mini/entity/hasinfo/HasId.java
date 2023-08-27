/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： HasId.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;

/**
 * 标识拥有标识的实体
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasId<T> {
  /**
   * 获取实体标识「ID」。
   *
   * @return 「ID」
   */
  T getId();

  /**
   * 设置实体标识「ID」。
   *
   * @param id
   *     实体标识「ID」
   * @throws UnsupportedOperationException
   *     当实现类不支持修改实体标识时抛出。
   */
  void setId(T id) throws UnsupportedOperationException;
}
