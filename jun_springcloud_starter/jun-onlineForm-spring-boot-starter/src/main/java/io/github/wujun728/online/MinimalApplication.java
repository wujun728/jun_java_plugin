package io.github.wujun728.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 最小化的Spring Boot应用
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MinimalApplication {

    /**
     * 内嵌的测试控制器
     */
    @RestController
    static class TestController {
        
        @GetMapping("/hello")
        public String hello() {
            return "Hello from Minimal Application!";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MinimalApplication.class, args);
    }
}