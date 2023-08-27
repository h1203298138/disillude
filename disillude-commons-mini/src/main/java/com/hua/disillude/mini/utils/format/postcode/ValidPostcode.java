/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： ValidPostcode.java
 * 模块说明：
 * 修改历史：
 * 2022年07月13日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.format.postcode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Hedh
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {PostcodeValidator.class})
public @interface ValidPostcode {
  String message() default "邮政编码格式错误";

  Class<?>[] groups() default {};

  boolean required() default false;

  Class<? extends Payload>[] payload() default {};
}
