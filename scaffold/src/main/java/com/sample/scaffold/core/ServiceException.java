package com.sample.scaffold.core;

import java.util.HashMap;
import java.util.Map;

public class ServiceException extends Exception {
    private String code = "DEFAULT_ERROR";
    private String message;

    private Map<String,String> exceptionMap = new HashMap<>();

    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(Exception exception) {
        this.message = exception.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String,String> getExceptionMap(){
        exceptionMap.put("code",code);
        exceptionMap.put("message",message);
        return exceptionMap;
    }

}
