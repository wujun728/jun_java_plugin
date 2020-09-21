/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author ZhangGaoFeng
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan("com.redis.proxy.monitor")
public class App extends WebMvcConfigurerAdapter {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/pics/**").addResourceLocations("classpath:/pics/");
                super.addResourceHandlers(registry);
        }

        public static void main(String... args) {
                System.gc();
                SpringApplication.run(App.class, args);
        }

}
