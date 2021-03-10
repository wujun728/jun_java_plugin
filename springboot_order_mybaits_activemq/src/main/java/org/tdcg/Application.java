package org.tdcg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: Application
 * @Package: org.tdcg
 * @Description: 启动类
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/31
 * @Version: V1.0
 */
@SpringBootApplication
@MapperScan("org.tdcg.mapper")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
