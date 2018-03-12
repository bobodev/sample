package com.sample.interceptor.core;

/**
 * Created by Jonathan on 2018/3/12.
 */
public enum ErrorUtil{

    /**
     * 错误枚举类
     */
    SERVER_ERROR(101, "服务错误"),
    APPLICATION_ERROR(102, "应用错误"),
    PARAMETER_ERROR(103, "参数错误"),
    SIGN_ERROR(104, "签名错误"),
    NO_PATH_FOUND(105, "请求地址不存在"),
    COMMON_PARAMETER_ERROR(106, "通用参数错误"),
    METHOD_NOT_ALLOWED(107, "请求方式不支持"),
    DEAL_FAIL(107, "处理失败"),
    DATA_NOT_EXIST(108, "数据不存在"),
    FILE_NOT_IMAGE(109, "文件不为图片"),
    NETWORK_TIMEOUT(110, "网络连接超时"),
    DO_NOT_REPEAT(111, "请勿重复操作"),
    FILE_NOT_EXIT(112, "文件不存在"),
    FILE_TOO_LARGE(113, "文件过大"),

    NOT_LOGIN(201, "未登录"),
    NO_GATADMIN(202, "不是关爱通平台管理员"),
    NO_MANAGER(203, "不是入职应用管理员"),
    NOT_ALLOW(204, "禁止访问"),
    NOT_DATA(205, "没有数据"),

    MOBILE_NULL(301, "手机号为空"),
    MOBILE_ERROR(302, "手机号错误"),
    EMAIL_NULL(303, "邮箱为空"),
    EMAIL_ERROR(304, "邮箱错误"),
    REMARK_NULL(307, "备注为空"),
    REMARK_ERROR(308, "备注字数过多"),
    PAGE_TIMEOUT(309, "页面超时");

    private int code;

    private String msg;

    ErrorUtil(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getErrorCode() {
        return code;
    }

    public String getErrorMsg() {
        return msg;
    }
}
