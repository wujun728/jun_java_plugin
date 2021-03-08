package com.sam.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value={"com.sam.demo"})
public class AppStart {
	
	public static void main(String[] args) {
		SpringApplication.run(AppStart.class, args);
		
	}
}
