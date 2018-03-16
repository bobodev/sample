package com.sample.scaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan({"com.sample.scaffold"})
public class Application extends WebMvcConfigurerAdapter {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(new Object[]{Application.class}, args);
        System.in.read(); // 按任意键退出
    }
}