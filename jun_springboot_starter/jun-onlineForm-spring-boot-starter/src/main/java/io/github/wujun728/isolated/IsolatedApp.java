package io.github.wujun728.isolated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 完全隔离的Spring Boot应用，只包含必要的组件
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("io.github.wujun728.isolated")
public class IsolatedApp {

    /**
     * 内嵌的控制器
     */
    @RestController
    static class IsolatedController {
        @GetMapping("/isolated/hello")
        public String hello() {
            return "Hello from isolated controller!";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(IsolatedApp.class, args);
    }
}