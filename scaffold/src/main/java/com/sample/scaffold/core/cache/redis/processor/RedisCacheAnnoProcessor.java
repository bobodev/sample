package com.sample.scaffold.core.cache.redis.processor;

import com.sample.scaffold.core.cache.redis.annotation.RedisCacheAnno;
import com.sample.scaffold.service.technology.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RedisCacheAnnoProcessor {

    private final static Logger logger = LoggerFactory.getLogger(RedisCacheAnnoProcessor.class);

    @Autowired
    private RedisService redisService;

    @Around("@annotation(com.sample.scaffold.core.cache.redis.annotation.RedisCacheAnno)")
    public Object dealExceptionForController(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = null;
        Method currentMethod = null;
        Class<?> aClass = pjp.getTarget().getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(pjp.getSignature().getName())) {
                currentMethod = method;
                break;
            }
        }
        RedisCacheAnno redisCacheAnno = currentMethod.getAnnotation(RedisCacheAnno.class);
        String key = redisCacheAnno.key();
        int expired = redisCacheAnno.expired();
        //判断缓存有没有，有的话从缓存取
        proceed = pjp.proceed();
        //数据放入缓存
        return proceed;
    }
}