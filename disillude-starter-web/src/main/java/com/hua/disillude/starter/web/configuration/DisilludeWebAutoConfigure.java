/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： DisilludeWebAutoConfigure.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration;

import com.hua.disillude.biz.spring.ApplicationContextUtils;
import com.hua.disillude.starter.web.configuration.filter.AccessConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hedh
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackageClasses = {DisilludeWebAutoConfigure.class, AccessConfiguration.class, ApplicationContextUtils.class})
public class DisilludeWebAutoConfigure {

}
