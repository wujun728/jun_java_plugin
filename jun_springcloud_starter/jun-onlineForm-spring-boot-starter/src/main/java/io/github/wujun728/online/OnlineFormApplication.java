package io.github.wujun728.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 最小化的Spring Boot启动类
 * 移除了所有可能导致依赖注入问题的配置
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OnlineFormApplication {

    /**
     * 内嵌的测试控制器
     */
    @RestController
    static class TestController {
        
        @GetMapping("/hello")
        public String hello() {
            return "Hello from Minimal Application!";
        }
        
        @GetMapping("/test")
        public String test() {
            return "Test endpoint is working!";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineFormApplication.class, args);
    }

}