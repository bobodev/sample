package com.sample.scaffold.core.validate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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

}
