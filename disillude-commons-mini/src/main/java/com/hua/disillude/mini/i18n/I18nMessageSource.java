/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： I18nMessageSource.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.i18n;

/**
 * @author Hedh
 * @since 1.0
 */
public interface I18nMessageSource {
  String getMessage(String moduleCode, String field, Object[] params);

  String getMessage(String appId, String moduleCode, String field, Object[] params);

  String getMessage(String message, Object[] params);
}
