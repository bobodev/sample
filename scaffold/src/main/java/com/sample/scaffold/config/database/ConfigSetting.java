package com.sample.scaffold.config.database;

/**
 * Created by sunguangzhu on 2017/9/4.
 */
public class ConfigSetting {
    private String mysqlUrl;
    private String mysqlDriverClassName;
    private String mysqlUserName;
    private String mysqlPassword;

    public String getMysqlUrl() {
        return mysqlUrl;
    }

    public void setMysqlUrl(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
    }

    public String getMysqlDriverClassName() {
        return mysqlDriverClassName;
    }

    public void setMysqlDriverClassName(String mysqlDriverClassName) {
        this.mysqlDriverClassName = mysqlDriverClassName;
    }

    public String getMysqlUserName() {
        return mysqlUserName;
    }

    public void setMysqlUserName(String mysqlUserName) {
        this.mysqlUserName = mysqlUserName;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public void setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
    }

}
