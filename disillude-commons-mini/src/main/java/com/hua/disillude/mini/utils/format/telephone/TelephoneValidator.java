/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： TelephoneValidator.java
 * 模块说明：
 * 修改历史：
 * 2022年07月13日 - HeDongHua - 创建。
 */
package com.hua.disillude.mini.utils.format.telephone;

import com.hua.disillude.mini.utils.StringUtil;
import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HeDongHua
 * @since 1.0
 */
@Data
public class TelephoneValidator implements ConstraintValidator<ValidTelephone, String> {
  public static final String REGULAR = "^((13[0-9])|(14[579])|(15[^4])|(16[6])|(17[01235678])|(18[0-9])|(19[8,9]))\\d{8}$";
  private boolean required;

  @Override
  public void initialize(ValidTelephone annotation) {
    this.setRequired(annotation.required());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtil.isNullOrBlank(value)) {
      return !this.required;
    }
    return this.isValidUsername(value);
  }

  private boolean isValidUsername(String value) {
    Pattern pattern = Pattern.compile(TelephoneValidator.REGULAR);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}