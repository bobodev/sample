package com.sample.scaffold;

import com.alibaba.fastjson.JSON;
import com.sample.scaffold.config.cache.DefaultCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan({"com.sample.scaffold"})
@EnableCaching
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(new Object[]{Application.class}, args);
        new Thread(() -> {
            while (true) {
                ConcurrentMap<Object, Object> store = DefaultCacheManager.store;
                System.out.println(" DefaultCacheManager.store = " + JSON.toJSONString(DefaultCacheManager.store));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.in.read(); // 按任意键退出
    }
}