package com.sample.interceptor.core;

public class BaseExcelModel {
    private String errorMsg;//错误信息，如果需要显示错误信息，加上这一行

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
