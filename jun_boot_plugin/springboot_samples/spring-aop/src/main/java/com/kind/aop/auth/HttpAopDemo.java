package com.kind.aop.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@SpringBootApplication
public class HttpAopDemo {

    public static void main(String[] args) {
        SpringApplication.run(HttpAopDemo.class, args);
    }
}
