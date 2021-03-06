package com.sample.scaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan({"com.sample.scaffold"})
@EnableCaching
@EnableJpaAuditing
@EnableScheduling
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(new Object[]{Application.class}, args);
//        new Thread(() -> {
//            while (true) {
//                ConcurrentMap<Object, Object> store = DefaultCacheManager.store;
//                System.out.println(" DefaultCacheManager.store = " + JSON.toJSONString(DefaultCacheManager.store));
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        System.in.read(); // 按任意键退出
    }
}