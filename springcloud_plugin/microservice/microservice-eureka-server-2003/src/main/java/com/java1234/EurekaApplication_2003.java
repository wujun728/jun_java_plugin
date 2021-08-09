package com.java1234;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
 
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication_2003 {
 
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication_2003.class, args);
    }
}