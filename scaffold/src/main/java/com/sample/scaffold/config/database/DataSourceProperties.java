package com.sample.scaffold.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "scaffold.datasource")
@Configuration
public class DataSourceProperties extends ConfigSetting{
    public DataSourceProperties() {
        super();
    }
}
