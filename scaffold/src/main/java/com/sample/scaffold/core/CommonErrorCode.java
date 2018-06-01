package com.sample.scaffold.core;

public enum  CommonErrorCode {

    SYSTEM_ERROR(1028, 1, 10001, "内部服务错误"),
    SIGNATURE_ERROR(1028, 1, 10002, "签名错误"),
    NO_PATH_FOUND(1028, 1, 10003, "请求地址不存在"),
    METHOD_NOT_ALLOWED(1028, 1, 10004, "请求方式不支持"),
    PARAMETER_ERROR(1028, 1, 10005, "参数错误"),
    COMMON_PARAMETER_ERROR(1028, 1, 10006, "通用参数错误"),
    PARAMETER_TYPE_ERROR(1028, 1, 10007, "参数类型错误"),
    PARAMETER_NOT_PRESENT_ERROR(1028, 1, 10008, "参数缺失"),
    DEPENDENCY_SERVICE_ERROR(1028, 3, 10001, "依赖服务错误");

    private int appCode;

    private int errorType;

    private int bizCode;

    private String message;

    CommonErrorCode(int appCode, int errorType, int bizCode, String message) {
        this.appCode = appCode;
        this.errorType = errorType;
        this.bizCode = bizCode;
        this.message = message;
    }

    public int getErrorCode() {
        return bizCode;
    }

    public String getErrorMsg() {
        return message;
    }
}
