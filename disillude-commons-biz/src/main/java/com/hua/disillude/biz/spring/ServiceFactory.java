/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： ServiceFactory.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.biz.spring;

import org.springframework.stereotype.Component;

/**
 * @author Hedh
 * @since 1.0
 */
@Component
public class ServiceFactory {

  public static <T> T getService(Class<T> requiredType) {
    return ApplicationContextUtils.getBean(requiredType);
  }

  public static <T> T getService(String tenant, Class<T> requiredType) {
    assert tenant != null;
    return ApplicationContextUtils.getBean(requiredType);
  }

  public static <T> T getServiceById(String serviceId, Class<T> requiredType) {
    assert serviceId != null;
    return ApplicationContextUtils.getBean(serviceId, requiredType);
  }
}
