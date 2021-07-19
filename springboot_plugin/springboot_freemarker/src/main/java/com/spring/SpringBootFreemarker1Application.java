package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.spring"})
public class SpringBootFreemarker1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFreemarker1Application.class, args);
	}
}
