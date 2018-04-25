package com.sample.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * HikariDataSource配置
 * @author wanchongyang
 * @date 2018/1/10
 */
@Component
@ConfigurationProperties(prefix = "jdbc")
public class HikariDataSourceProperties {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接池名称
     */
    private String poolName;

    /**
     * 连接地址
     */
    private String url;

    /**
     * dataSource名称
     */
    private String dataSourceClassName;

    /**
     * 加载驱动
     */
    private String driverClassName;

    /**
     * 连接超时时间 Default: 30000 (30 seconds)
     */
    private int connectionTimeout;

    /**
     * 连接的最大存活时间 Default: 1800000 (30 minutes)
     */
    private int maxLifetime;

    /**
     * 连接池允许的最大连接总数（包含空闲和使用中）
     */
    private int maximumPoolSize;

    /**
     * 连接池中保留的最小空闲连接数
     */
    private int minimumIdle;

    /**
     * 连接设置为空闲 Default: 600000 (10 minutes) 最小值为10s
     */
    private int idleTimeout;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    public void setDataSourceClassName(String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
