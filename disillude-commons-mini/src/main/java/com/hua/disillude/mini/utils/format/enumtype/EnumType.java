/**
 * 版权所有(C)，华仔不脱发科技有限公司，2021，所有权利保留。
 * <p>
 * 项目名： scm-project
 * 文件名： EnumType.java
 * 模块说明：
 * 修改历史：
 * 2021年06月18日 - Fu Yuanfa - 创建。
 */
package com.hua.disillude.mini.utils.format.enumtype;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Fu Yuanfa
 * @since 1.0
 */
@Documented
@Constraint(validatedBy = {EnumTypeValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface EnumType {

  /**
   * 映射到的枚举类型
   */
  Class<? extends Enum> value();

  String message() default "错误的枚举值";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
