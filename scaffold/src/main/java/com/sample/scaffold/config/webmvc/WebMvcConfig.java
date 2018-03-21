package com.sample.scaffold.config.webmvc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sample.scaffold.controller.interceptor.LoginInterceptor;
import com.sample.scaffold.core.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<ServiceException> jsonErrorHandler(Exception exception) throws Exception {
        ServiceException serviceException;
        if (exception instanceof ServiceException) {
            serviceException = (ServiceException) exception;
        } else {
            serviceException = new ServiceException(exception);
            serviceException.setMessage(exception.getMessage());
        }
        ResponseEntity<ServiceException> responseEntity = ResponseEntity.badRequest().body(serviceException);
        return responseEntity;
    }

    /**
     * 用户处理JSON序列化
     * @return
     */
    @Bean
    public HttpMessageConverters httpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
}
