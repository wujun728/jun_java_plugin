package io.springside.springtime.examples.helloservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.springside.springtime.springboot.SpringTimeConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Import(SpringTimeConfiguration.class)
@EnableSwagger2
public class HelloServiceApp {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(HelloServiceApp.class, args);
	}
}
