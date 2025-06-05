package com.demo;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
