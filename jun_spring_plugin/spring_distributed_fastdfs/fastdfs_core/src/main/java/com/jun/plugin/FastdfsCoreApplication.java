package com.jun.plugin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.hengxunda.dfs.core.mapper")
public class FastdfsCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastdfsCoreApplication.class, args);
    }
}
