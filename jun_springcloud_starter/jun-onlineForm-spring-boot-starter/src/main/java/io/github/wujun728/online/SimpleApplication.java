package io.github.wujun728.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 最简单的Spring Boot启动类
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"io.github.wujun728.online"})
public class SimpleApplication {

    /**
     * 内嵌的测试控制器
     */
    @RestController
    public static class TestController {
        
        @GetMapping("/hello")
        public String hello() {
            return "Hello, application is running!";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}