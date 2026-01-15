package io.github.wujun728.minimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 最小化的Spring Boot应用，仅用于测试
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MinimalApp {

    public static void main(String[] args) {
        SpringApplication.run(MinimalApp.class, args);
    }
}