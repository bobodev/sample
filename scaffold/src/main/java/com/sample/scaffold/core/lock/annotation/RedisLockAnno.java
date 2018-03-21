package com.sample.scaffold.core.lock.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface RedisLockAnno {

    /**
     * redis key
     *
     * @return
     */
    String key();

    /**
     * 过期时间 毫秒
     *
     * @return
     */
    int expired();

    /**
     * 是否执行
     *
     * @return
     */
    String execKey() default "";
}