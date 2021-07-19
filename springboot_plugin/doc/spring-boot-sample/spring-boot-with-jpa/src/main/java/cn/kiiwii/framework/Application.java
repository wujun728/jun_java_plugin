package cn.kiiwii.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by zhong on 2016/11/24.
 */
@Configuration
@ComponentScan(basePackages="cn.kiiwii.framework")
@EnableAutoConfiguration
@EntityScan(basePackages="cn.kiiwii.framework.model")
@EnableJpaRepositories(basePackages="cn.kiiwii.framework.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
