package com.km;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootGuavaCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGuavaCacheApplication.class, args);
    }
}
