package com.sample.scaffold.core.cache.redis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface RedisCacheAnno {

    /**
     * redis key
     *
     * @return
     */
    String key();

    /**
     * redis value
     *
     * @return
     */
    String value() default "";

    /**
     * 过期时间 秒,默认1分钟
     *
     * @return
     */
    int expired() default 60;
}