package com.km;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootCaffeineCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCaffeineCacheApplication.class, args);
    }
}
