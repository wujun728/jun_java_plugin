package com.hrhx.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.web.WebAppConfiguration;
 
@SpringBootApplication
@WebAppConfiguration
@EnableScheduling
@EnableAsync
public class Application{
	public static void main(String[] args) {  
        SpringApplication.run(Application.class);  
    }  
}
