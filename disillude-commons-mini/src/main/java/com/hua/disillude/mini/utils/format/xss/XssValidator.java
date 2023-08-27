/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： XssValidator.java
 * 模块说明：
 * 修改历史：
 * 2022年07月03日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.format.xss;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义xss校验注解实现
 *
 * @author Hedh
 * @since 1.0
 */
public class XssValidator implements ConstraintValidator<Xss, String> {
  private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true;
    }
    return !containsHtml(value);
  }

  public static boolean containsHtml(String value) {
    Pattern pattern = Pattern.compile(HTML_PATTERN);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}