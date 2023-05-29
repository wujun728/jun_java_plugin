package com.jun.plugin.springbootjunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jun.plugin.springbootjunit.**")
public class SpringbootJunitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJunitApplication.class, args);
	}
}
