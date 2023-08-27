/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： AccessConfiguration.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.filter;

import com.hua.disillude.biz.access.AccessUser;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.util.TagUtils;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

/**
 * @author Hedh
 * @since 1.0
 */
@Configuration
public class AccessConfiguration {

  @Bean(AccessUser.BEAN_NAME)
  @Scope(value = TagUtils.SCOPE_REQUEST, proxyMode = TARGET_CLASS)
  public AccessUser accessUser() {
    return new AccessUser();
  }

  @Bean
  public TraceFilter traceFilter() {
    return new TraceFilter();
  }

  @Bean
  public FilterRegistrationBean<TraceFilter> registerTraceFilter(TraceFilter traceFilter) {
    FilterRegistrationBean<TraceFilter> registrationBean =
        new FilterRegistrationBean<TraceFilter>();
    registrationBean.setFilter(traceFilter);
    registrationBean.setOrder(0);
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
