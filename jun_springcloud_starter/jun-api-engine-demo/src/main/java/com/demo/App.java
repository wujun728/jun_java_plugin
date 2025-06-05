package com.demo;

import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.utils.DataSourcePool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

import static io.github.wujun728.db.utils.DataSourcePool.main;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
//        init();
    }

    public static void init() {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        //String sqlId = Db.queryStr("select sql_text from api_sql  limit 1 ");
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        Db.init(main,dataSource);
    }
}
