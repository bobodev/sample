package com.sample.scaffold.core;

public class ServiceException extends Exception {
    private String code = "DEFAULT_ERROR";
    private String message;

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
}
