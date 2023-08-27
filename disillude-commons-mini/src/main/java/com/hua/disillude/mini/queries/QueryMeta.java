package com.hua.disillude.mini.queries;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryMeta {

  String property();

  String name();

  String cop() default "=";

  String fieldType() default "input";

  String refObject() default "";

  String params() default "";
}
