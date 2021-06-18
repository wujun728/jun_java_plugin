package com.kancy.emailplus.demo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Wujun
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class SpringBootEmailPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEmailPlusDemoApplication.class, args);
    }

}
