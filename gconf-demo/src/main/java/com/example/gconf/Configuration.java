package com.example.gconf;


import com.ciicgat.sdk.gconf.ConfigApp;
import com.ciicgat.sdk.gconf.spring.GConfPropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

@org.springframework.context.annotation.Configuration
@PropertySource("classpath:contactlist.properties" )
public class Configuration {

    @Bean("setting")
    public PropertiesFactoryBean propertiesFactoryBean(){
        GConfPropertiesFactoryBean propertiesFactoryBean=new GConfPropertiesFactoryBean();

        List<ConfigApp> configApps =new ArrayList<>();
        ConfigApp configApp=new ConfigApp("contactlist-provider","1.0.0");
        configApps.add(configApp);
        propertiesFactoryBean.setConfigApps(configApps);
        return propertiesFactoryBean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }
}
