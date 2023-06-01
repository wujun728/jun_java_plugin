package com.cosmoplat;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo //开启dubbo的注解支持
public class ApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);

    }
}

