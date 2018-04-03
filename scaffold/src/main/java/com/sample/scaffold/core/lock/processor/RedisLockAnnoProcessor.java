package com.sample.scaffold.core.lock.processor;

import com.sample.scaffold.core.lock.annotation.RedisLockAnno;
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
public class RedisLockAnnoProcessor {

    private final static Logger logger = LoggerFactory.getLogger(RedisLockAnnoProcessor.class);

    @Autowired
    private RedisService redisService;

    @Around("@annotation(com.sample.scaffold.core.lock.annotation.RedisLockAnno)")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
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
        RedisLockAnno redisLockAnno = currentMethod.getAnnotation(RedisLockAnno.class);
        String key = redisLockAnno.key();
        int expired = redisLockAnno.expired();
        //开启redis锁
        logger.info(new StringBuilder(pjp.getTarget().getClass().getSimpleName()).append(".").append(currentMethod.getName())
                .append("#").append(" begin ").toString());
        boolean lock = redisService.getLock(key, expired);
        if (!lock) {
            logger.info(new StringBuilder(pjp.getTarget().getClass().getSimpleName()).append(".").append(currentMethod.getName())
                    .append("#").append(" can not get lock ").toString());
            return null;
        }
        try {
            proceed = pjp.proceed();
            logger.info(new StringBuilder(pjp.getTarget().getClass().getSimpleName()).append(".").append(currentMethod.getName())
                    .append("#").append(" end ").toString());
        } catch (Exception e) {
            logger.error(new StringBuilder(pjp.getTarget().getClass().getSimpleName()).append(".").append(currentMethod.getName())
                    .append("#").append(" failed at e:{} ").toString(), e);
        } finally {
            //释放redis锁
            logger.info(new StringBuilder(pjp.getTarget().getClass().getSimpleName()).append(".").append(currentMethod.getName())
                    .append("#").append(" release lock ").toString());
            redisService.del(key);
        }
        return proceed;
    }
}