package com.sample.scaffold.core.validate.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ValidateAnno {
    boolean cascadeValidate() default true;//用在方法上，说明是否开启级联校验
    boolean fastFail() default true;//使用与method，快速失败，针对级联校验
}