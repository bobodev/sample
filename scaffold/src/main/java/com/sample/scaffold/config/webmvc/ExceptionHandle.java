package com.sample.scaffold.config.webmvc;

import com.sample.scaffold.core.ServiceException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<Object> jsonErrorHandler(Exception exception) throws Exception {
        exception.printStackTrace();
        ServiceException serviceException;
        if (exception instanceof ServiceException) {
            serviceException = (ServiceException) exception;
        } else {
            serviceException = new ServiceException(exception);
            serviceException.setMessage(exception.getMessage());
        }
        ResponseEntity<Object> responseEntity = ResponseEntity.badRequest().body(serviceException.getExceptionMap());
        return responseEntity;
    }

}
