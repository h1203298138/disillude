/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： TraceFilter.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.filter;

import com.hua.disillude.biz.access.AccessContext;
import com.hua.disillude.biz.access.AccessUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Hedh
 * @since 1.0
 */
@Slf4j
public class TraceFilter implements Filter {

  @Resource(name = AccessUser.BEAN_NAME)
  private AccessUser accessUser;

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String traceId = request.getHeader("trace_id");
    if (StringUtils.isBlank(traceId)) {
      traceId = UUID.randomUUID().toString().replace("-", "");
    }
    MDC.put("trace_id", traceId);
    this.accessUser.setAppId(this.decode(request.getHeader("appId")));
    this.accessUser.setTenant(this.decode(request.getHeader("tenant")));
    this.accessUser.setOrgId(this.decode(request.getHeader("orgId")));
    this.accessUser.setOrgId(this.decode(request.getHeader("orgName")));
    this.accessUser.setUserId(this.decode(request.getHeader("userId")));
    this.accessUser.setUserCode(this.decode(request.getHeader("userCode")));
    this.accessUser.setUserName(this.decode(request.getHeader("userName")));
    this.accessUser.setLang(request.getHeader("lang"));
    AccessContext.setAccessUser(this.accessUser);
    filterChain.doFilter(request, servletResponse);
  }

  @Override
  public void destroy() {
  }

  private String decode(String str) {
    if (str == null) {
      return null;
    }
    return URLDecoder.decode(str, StandardCharsets.UTF_8);
  }
}
