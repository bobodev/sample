package com.sample.scaffold.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "common")
@Configuration
public class CommonProperties {
}
