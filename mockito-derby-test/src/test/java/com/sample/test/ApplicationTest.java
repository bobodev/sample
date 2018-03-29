package com.sample.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用程序启动类
 * @author wanchongyang
 * @date 2018/2/28
 */
@EnableTransactionManagement
@SpringBootApplication
//@EnableAsync(proxyTargetClass = true)
@ComponentScan(basePackages = "com.sample.test")
public class ApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
}
