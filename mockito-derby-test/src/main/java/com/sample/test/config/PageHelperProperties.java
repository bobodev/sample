package com.sample.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 分页插件参数对象
 * Created by wanchongyang on 2017/9/11.
 */
@Component
@ConfigurationProperties(prefix = "pagehelper")
public class PageHelperProperties {
    private String helperDialect;
    private String reasonable;
    private String supportMethodsArguments;
    private String params;
    private String autoRuntimeDialect;

    public String getHelperDialect() {
        return helperDialect;
    }

    public void setHelperDialect(String helperDialect) {
        this.helperDialect = helperDialect;
    }

    public String getReasonable() {
        return reasonable;
    }

    public void setReasonable(String reasonable) {
        this.reasonable = reasonable;
    }

    public String getSupportMethodsArguments() {
        return supportMethodsArguments;
    }

    public void setSupportMethodsArguments(String supportMethodsArguments) {
        this.supportMethodsArguments = supportMethodsArguments;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getAutoRuntimeDialect() {
        return autoRuntimeDialect;
    }

    public void setAutoRuntimeDialect(String autoRuntimeDialect) {
        this.autoRuntimeDialect = autoRuntimeDialect;
    }
}
