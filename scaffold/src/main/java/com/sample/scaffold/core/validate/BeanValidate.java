package com.sample.scaffold.core.validate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Created by sunguangzhu on 15/8/23.
 */
public class BeanValidate<T> {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static BeanValidate beanValidate;

    private BeanValidate() {
    }

    public static BeanValidate getInstance() {
        if (beanValidate == null) {
            beanValidate = new BeanValidate();
        }
        return beanValidate;
    }

    public static Validator getValidator() {
        return validator;
    }

    public List<String> validate(Object object, List<String> errors) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            errors.add(constraintViolation.getMessage());
        }
        return errors;
    }

    //进行参数校验
    public  List<String> validMethodParams(Object obj, Method method, Object [] params,List<String> errors){
        ExecutableValidator validatorParam = validator.forExecutables();
        Set<ConstraintViolation<Object>> constraintViolations = validatorParam.validateParameters(obj, method, params);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            errors.add(constraintViolation.getMessage());
        }
        return errors;
    }

}
