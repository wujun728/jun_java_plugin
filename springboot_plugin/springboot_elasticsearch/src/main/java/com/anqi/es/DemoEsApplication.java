package com.anqi.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author anqi
 */
@SpringBootApplication(scanBasePackages = "com.anqi.es")
public class DemoEsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoEsApplication.class, args);
    }

}
