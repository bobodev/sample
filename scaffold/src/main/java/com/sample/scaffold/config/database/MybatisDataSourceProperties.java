package com.sample.scaffold.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "scaffold.mybatis.datasource")
@Configuration
public class MybatisDataSourceProperties extends ConfigSetting {

    public MybatisDataSourceProperties() {
        super();
    }

}
