package com.cosmoplat.provider;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo //开启dubbo的注解支持
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.cosmoplat.provider.mapper")
public class SysProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysProviderApplication.class, args);
    }
}