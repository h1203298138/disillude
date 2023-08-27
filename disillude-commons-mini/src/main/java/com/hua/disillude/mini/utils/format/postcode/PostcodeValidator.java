/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： PostcodeValidator.java
 * 模块说明：
 * 修改历史：
 * 2022年07月13日 - HeDongHua - 创建。
 */
package com.hua.disillude.mini.utils.format.postcode;

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
public class PostcodeValidator implements ConstraintValidator<ValidPostcode, String> {
  public static final String REGULAR = "^(?!_)(?!.*?_$)^[a-zA-Z0-9_]{5,32}";

  private boolean required;

  @Override
  public void initialize(ValidPostcode annotation) {
    this.setRequired(annotation.required());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtil.isNullOrBlank(value)) {
      return !this.required;
    }
    return this.isValidPostcode(value);
  }

  private boolean isValidPostcode(String value) {
    Pattern pattern = Pattern.compile(PostcodeValidator.REGULAR);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
