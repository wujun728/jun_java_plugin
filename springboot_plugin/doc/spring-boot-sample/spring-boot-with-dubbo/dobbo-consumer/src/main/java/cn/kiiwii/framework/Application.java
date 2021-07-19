package cn.kiiwii.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhong on 2016/11/22.
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"cn.kiiwii.framework","cn.kiiwii.framework.dubbo"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
