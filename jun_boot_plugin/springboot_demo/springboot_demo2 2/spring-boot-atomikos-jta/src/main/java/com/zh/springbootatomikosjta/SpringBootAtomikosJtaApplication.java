package com.zh.springbootatomikosjta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringBootAtomikosJtaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAtomikosJtaApplication.class, args);
    }

}
