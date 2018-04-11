package com.sample.scaffold.config.webmvc;

import com.sample.scaffold.controller.interceptor.LoginInterceptor;
import com.sample.scaffold.core.MyArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludeRequestPath = new String[]{
        };
        String[] includeRequestPath = new String[]{
                "/api/scaffold/**/*"
        };
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(excludeRequestPath)
                .addPathPatterns(includeRequestPath);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        // 注册JsonPathArgumentResolver的参数分解器
        argumentResolvers.add(new MyArgumentResolver());
    }
}
