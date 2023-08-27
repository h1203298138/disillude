/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： HasCode.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;


import com.hua.disillude.mini.entity.injectable.Injectable;

/**
 * 标识拥有编码/单号的实体
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasCode extends Injectable {
  /**
   * 获取编码/单号「Code」。
   *
   * @return 「Code」
   */
  String getCode();

  /**
   * 设置编码/单号「Code」。
   *
   * @param code
   *     编码/单号「Code」
   * @throws UnsupportedOperationException
   *     当实现类不支持修改编码/单号时抛出。
   */
  void setCode(String code) throws UnsupportedOperationException;
}
