package com.java1234.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.java1234.filter.AccessFilter;
 
/**
 * Zuul配置
 * @author Administrator
 *
 */
@Configuration
public class ZuulConfig {
 
    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }
}