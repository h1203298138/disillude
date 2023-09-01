/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： HasTenant.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;

/**
 * @author Hedh
 * @since 1.0
 */
public interface HasTenant {
  /**
   * 获取租户「Tenant」。
   *
   * @return 「Tenant」
   */
  String getTenant();

  /**
   * 设置租户「Tenant」。
   *
   * @param tenant
   *     租户「Tenant」
   * @throws UnsupportedOperationException
   *     当实现类不支持修改租户时抛出。
   */
  void setTenant(String tenant) throws UnsupportedOperationException;
}
