/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： PasswordValidator.java
 * 模块说明：
 * 修改历史：
 * 2022年07月08日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.format.password;

import com.hua.disillude.mini.utils.StringUtil;
import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
  private String regular;
  private boolean required;

  @Override
  public void initialize(ValidPassword annotation) {
    this.setRegular(annotation.strength().getRegular());
    this.setRequired(annotation.required());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtil.isNullOrBlank(value)) {
      if (StringUtil.isNullOrBlank(this.getRegular())) {
        return true;
      }
      return !this.required;
    }
    return isValidPassword(this.getRegular(), value);
  }

  public static boolean isValidPassword(String regex, String value) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}