package com.sample.easypoi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class DefaultTaskConfig implements AsyncConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(DefaultTaskConfig.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setThreadNamePrefix("defaultTaskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (arg0, arg1, arg2) -> {
            logger.error("==========================" + arg0.getMessage() + "=======================", arg0);
            logger.error("exception method:" + arg1.getName());
        };
    }

    @Bean(value = "defaultTaskExecutor")
    public Executor productTaskExecutor() {
        return this.getAsyncExecutor();
    }
}