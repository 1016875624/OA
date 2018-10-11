package com.oa;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OaApplication {

	public static void main(String[] args) {
		System.out.println(OaApplication.class.getClassLoader().getResource("").getPath());
		SpringApplication.run(OaApplication.class, args);
	}
}
