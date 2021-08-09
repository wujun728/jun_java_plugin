package com.java1234;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class StudentProviderHystrixApplication_1005 {

	public static void main(String[] args) {
		SpringApplication.run(StudentProviderHystrixApplication_1005.class, args);
	}
}
