/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： Injectable.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.injectable;

/**
 * 标识可反射的实体
 *
 * @author Hedh
 * @since 1.0
 */
public interface Injectable {
  /**
   * 注入对象
   *
   * @param o
   *     注入对象
   * @throws UnsupportedOperationException
   *     不支持的操作异常
   */
  void inject(Object o) throws UnsupportedOperationException;
}
