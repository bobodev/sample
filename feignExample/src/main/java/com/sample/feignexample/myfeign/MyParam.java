package com.sample.feignexample.myfeign;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by issac.hu on 2018/3/29.
 */

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface MyParam {
    String value();
}
