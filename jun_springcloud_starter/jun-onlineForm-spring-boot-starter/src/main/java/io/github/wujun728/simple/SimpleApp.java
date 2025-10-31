package io.github.wujun728.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 完全独立的Spring Boot应用
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SimpleApp {

    /**
     * 内嵌的测试控制器
     */
    @RestController
    static class SimpleController {
        
        @GetMapping("/hello")
        public String hello() {
            return "Hello from Simple App!";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleApp.class, args);
    }
}