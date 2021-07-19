package com.buxiaoxa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * Created by xw on 2017/8/9.
 * 2017-08-24 15:36
 */
@EnableZipkinStreamServer
@SpringBootApplication
public class ZipkinServerApplication {

    public static void main(String[] args){
        SpringApplication.run(ZipkinServerApplication.class,args);
    }
}