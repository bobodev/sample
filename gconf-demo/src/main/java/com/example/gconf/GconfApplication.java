package com.example.gconf;

import com.ciicgat.sdk.gconf.spring.GConfContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GconfApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(GconfApplication.class);
		springApplication.addInitializers(new GConfContextInitializer("contactlist-provider","1.0.0"));
		springApplication.run(args);
	}
}
