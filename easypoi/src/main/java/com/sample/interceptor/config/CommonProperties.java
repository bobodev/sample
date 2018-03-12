package com.sample.interceptor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "common")
@Configuration
public class CommonProperties {
    private String exportDir;
}
