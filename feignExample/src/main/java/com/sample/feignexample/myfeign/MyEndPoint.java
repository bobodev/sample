package com.sample.feignexample.myfeign;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by issac.hu on 2018/4/11.
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface MyEndPoint {
    String value();
}
