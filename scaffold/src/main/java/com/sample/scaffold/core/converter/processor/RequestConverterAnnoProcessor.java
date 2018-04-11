package com.sample.scaffold.core.converter.processor;

import com.sample.scaffold.core.converter.annotation.RequestConverterAnno;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestConverterAnnoProcessor {

    private final static Logger logger = LoggerFactory.getLogger(RequestConverterAnnoProcessor.class);

    @Around("@annotation(com.sample.scaffold.core.converter.annotation.RequestConverterAnno)")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        Object[] dataList = pjp.getArgs();
        for (Object data : dataList) {
            if (data.getClass().isAnnotationPresent(RequestConverterAnno.class)) {
            }
        }
        Object proceed = pjp.proceed();
        return proceed;
    }
}