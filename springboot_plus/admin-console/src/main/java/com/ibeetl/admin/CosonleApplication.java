package com.ibeetl.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CosonleApplication  extends SpringBootServletInitializer  {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CosonleApplication.class);
    }
    
    public static void main(String[] args) {
    	
        SpringApplication.run(CosonleApplication.class, args);
    }



}	