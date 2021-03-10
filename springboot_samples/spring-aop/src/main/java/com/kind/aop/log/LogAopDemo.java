package com.kind.aop.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;


@EnableAspectJAutoProxy
@SpringBootApplication
public class LogAopDemo {
    @Autowired
    NeedLogService needLogService;
    @Autowired
    NormalService normalService;

    public static void main(String[] args) {
        SpringApplication.run(LogAopDemo.class, args);
    }

    @PostConstruct
    public void test() {
        needLogService.logMethod("xys");
        try {
            needLogService.exceptionMethod();
        } catch (Exception e) {
            // Ignore
        }
        normalService.someMethod();
    }
}
