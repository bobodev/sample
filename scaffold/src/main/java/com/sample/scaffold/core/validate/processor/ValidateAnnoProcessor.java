package com.sample.scaffold.core.validate.processor;

import com.alibaba.fastjson.JSON;
import com.sample.scaffold.core.ServiceException;
import com.sample.scaffold.core.validate.BeanValidate;
import com.sample.scaffold.core.validate.annotation.ValidateAnno;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ValidateAnnoProcessor {

    private final static Logger logger = LoggerFactory.getLogger(ValidateAnnoProcessor.class);

    @Around("@annotation(com.sample.scaffold.core.validate.annotation.ValidateAnno)")
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
        if (currentMethod.isAnnotationPresent(ValidateAnno.class)) {
            ValidateAnno validate = currentMethod.getAnnotation(ValidateAnno.class);
            if (validate != null) {
                List<String> errors = new ArrayList<>();
                Object[] dataList = pjp.getArgs();
                for (Object data : dataList) {
                    //级联校验
                    if (validate.cascadeValidate()) {
                        cascadeValidate(data, errors, validate.fastFail());
                    } else {
                        //一级校验
                        BeanValidate.getInstance().validate(data, errors);
                    }
                    if (errors.size() > 0) {
                        throw new ServiceException(JSON.toJSONString(errors));
                    }
                }
            }
        }
        proceed = pjp.proceed();
        return proceed;
    }

    private void cascadeValidate(Object data, List<String> errors, boolean fastFail) throws Throwable {
        if (data == null) {
            return;
        }
        BeanValidate.getInstance().validate(data, errors);
        if (errors.size() > 0) {
            if (fastFail) {
                return;
            }
        }

        //二级校验
        Field[] declaredFields = data.getClass().getDeclaredFields();
        if (declaredFields != null) {
            for (Field declaredField : declaredFields) {
                ValidateAnno validate = declaredField.getAnnotation(ValidateAnno.class);
                if (validate != null) {
                    declaredField.setAccessible(true);
                    Object object = declaredField.get(data);
                    cascadeValidate(object, errors, fastFail);
                }
            }
        }
    }
}