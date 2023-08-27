/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： ValidPassword.java
 * 模块说明：
 * 修改历史：
 * 2022年07月08日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.format.password;

/**
 * @author Hedh
 * @since 1.0
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义密码格式校验注解
 *
 * @author Hedh
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {PasswordValidator.class})
public @interface ValidPassword {
  String message() default "密码格式错误";

  boolean required() default false;

  Class<?>[] groups() default {};

  PasswordStrength strength() default PasswordStrength.WEAK;

  Class<? extends Payload>[] payload() default {};
}
