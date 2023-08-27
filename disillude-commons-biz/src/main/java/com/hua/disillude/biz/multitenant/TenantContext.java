/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： TenantContext.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.biz.multitenant;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Hedh
 * @since 1.0
 */
@Slf4j
@Data
public class TenantContext {
  private static final ThreadLocal<TenantContext> holder = new ThreadLocal();

  public static void set(TenantContext ctx) {
    holder.set(ctx);
  }

  public static void setTenant(String tenant) {
    TenantContext ctx = get();
    if (ctx == null) {
      ctx = new TenantContext();
      ctx.tenant = tenant;
      set(ctx);
    }
    if (Objects.equals(ctx.tenant, tenant) == false) {
      ctx.tenant = tenant;
    }
  }

  public static TenantContext get() {
    return holder.get();
  }

  public static void clear() {
    holder.remove();
  }

  private String tenant;
}
