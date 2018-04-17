package com.sample.scaffold.config.webmvc;

import com.sample.scaffold.core.ServiceException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;

@Configuration
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<Object> jsonErrorHandler(Exception exception) throws Exception {
        ServiceException serviceException;
        if (exception instanceof ServiceException) {
            serviceException = (ServiceException) exception;
        } else if (exception instanceof UndeclaredThrowableException) {
            Throwable cause = exception.getCause();
            serviceException = new ServiceException(cause.getMessage());
        } else {
            serviceException = new ServiceException(exception);
            serviceException.setMessage(exception.getMessage());
        }
        ResponseEntity<Object> responseEntity = ResponseEntity.badRequest().body(serviceException.getExceptionMap());
        return responseEntity;
    }

}
