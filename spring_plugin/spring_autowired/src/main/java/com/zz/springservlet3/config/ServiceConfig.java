package com.zz.springservlet3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.zz.springservlet3", excludeFilters = @ComponentScan.Filter({Controller.class, RestController.class,
        Configuration.class, EnableWebMvc.class}))
@Import({PersistenceConfig.class, RedisConfig.class})
public class ServiceConfig {
}
