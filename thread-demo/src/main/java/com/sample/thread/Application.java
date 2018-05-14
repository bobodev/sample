package com.sample.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ComponentScan({"com.sample.thread"})
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(new Object[]{Application.class}, args);
        System.in.read(); // 按任意键退出
    }
}