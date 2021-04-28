package com.jun.plugin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan({"com.jun.plugin.*.mapper","com.jun.plugin.code.common.mapper"})  
@ComponentScan(basePackages ={"com.jun.plugin" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

