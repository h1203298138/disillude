/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： MultiTenantAspect.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.multitenant;

import com.hua.disillude.biz.multitenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Hedh
 * @since 1.0
 */
@Slf4j
@Aspect
@Order(-1)
@Component
public class MultiTenantAspect {

  @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && args(tenant,..)"
      + "||@within(com.hua.disillude.biz.multitenant.MultiTenant) && args(tenant,..)"
      + "||@within(com.hua.disillude.biz.shardingds.ShardingDb) && args(tenant,..)"
  )
  public void multiTenantPointCut(String tenant) {
  }

  @Before(value = "multiTenantPointCut(tenant)", argNames = "tenant")
  public void doBefore(String tenant) {
    log.info("multiTenantPointCut: {}", tenant);
    TenantContext.setTenant(tenant);
  }
}
