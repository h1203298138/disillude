/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： AccessContext.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.biz.access;

/**
 * @author Hedh
 * @since 1.0
 */
public class AccessContext {
  private static final ThreadLocal<AccessUser> holder = new ThreadLocal();

  public static void setAccessUser(AccessUser tenantId) {
    holder.set(tenantId);
  }

  public static AccessUser getAccessUser() {
    return holder.get();
  }

  public static void clear() {
    holder.remove();
  }
}
