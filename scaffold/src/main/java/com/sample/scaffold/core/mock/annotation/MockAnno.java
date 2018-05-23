package com.sample.scaffold.core.mock.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface MockAnno {

    /**
     * 文件名称，作为数据读取的key
     * @return
     */
    String fileName();
}