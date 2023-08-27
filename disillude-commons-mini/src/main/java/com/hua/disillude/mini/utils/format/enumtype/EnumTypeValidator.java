/**
 * 版权所有(C)，华仔不脱发科技有限公司，2021，所有权利保留。
 * <p>
 * 项目名： scm-project
 * 文件名： EnumTypeValidator.java
 * 模块说明：
 * 修改历史：
 * 2021年06月18日 - Fu Yuanfa - 创建。
 */
package com.hua.disillude.mini.utils.format.enumtype;

import com.hua.disillude.mini.utils.StringUtil;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Fu Yuanfa
 * @since 1.0
 */
public class EnumTypeValidator implements ConstraintValidator<EnumType, String> {

  private Class<? extends Enum> enumClass;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtil.isNullOrBlank(value) || this.enumClass == null) {
      return false;
    }
    return EnumUtils.isValidEnum(this.enumClass, value);
  }

  @Override
  public void initialize(EnumType constraintAnnotation) {
    this.enumClass = constraintAnnotation.value();
  }
}
