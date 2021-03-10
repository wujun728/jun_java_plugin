package com.zz.springservlet3.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan(basePackages = "com.zz.springservlet3", excludeFilters = @Filter({Controller.class, RestController.class,
        Configuration.class}))
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
@Import({ServiceConfig.class})
public class AppConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
