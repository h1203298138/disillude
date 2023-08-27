/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： LocaleMessage.java
 * 模块说明：
 * 修改历史：
 * 2022年01月31日 - Hedh - 创建。
 */
package com.hua.disillude.biz.i18n;


import com.hua.disillude.mini.spring.ApplicationContextUtils;

/**
 * 国际化消息封装类
 *
 * @author Hedh
 * @since 1.0
 */
public class LocaleMessage {

  public static String $amt(String appId, String moduleCode, String message, Object... params) {
    I18nMessageSource messageSource = ApplicationContextUtils.getBean(I18nMessageSource.class);
    return messageSource.getMessage(appId, moduleCode, message, params);
  }

  public static String $mt(String moduleCode, String message, Object... params) {
    I18nMessageSource messageSource = ApplicationContextUtils.getBean(I18nMessageSource.class);
    return messageSource.getMessage(moduleCode, message, params);
  }

  public static String $t(String message, Object... params) {
    I18nMessageSource messageSource = ApplicationContextUtils.getBean(I18nMessageSource.class);
    return messageSource.getMessage(message, params);
  }

}
