package com.sample.interceptor.Exception;

import com.sample.interceptor.core.ErrorUtil;

/**
 * Created by Jonathan on 2018/3/12.
 */
public class BusinessRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int errorCode;
    private String errorMsg;

    public BusinessRuntimeException(int errorCode, String errorMsg) {
        super(errorCode + "");
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessRuntimeException(ErrorUtil errorCode) {
        this(errorCode.getErrorCode(), errorCode.getErrorMsg());
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BusinessRuntimeException occurred, errorCode=" + this.errorCode + ",errorMsg=" + this.errorMsg);
        return sb.toString();
    }
}