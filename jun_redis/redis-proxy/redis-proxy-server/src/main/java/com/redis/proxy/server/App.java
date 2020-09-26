/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.server;

import com.redis.proxy.common.consts.CacheConsts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author ZhangGaoFeng
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan("com.redis.proxy.server")
@ImportResource("classpath:spring.xml")
public class App {

        /**
         * 启动读取传入参数
         *
         * @param args
         */
        private static void argsInit(String... args) {

                if (args == null || args.length == 0) {
                        return;
                }
                for (int index = 0; index < args.length; index++) {
                        if ("-group".equals(args[index])) {
                                CacheConsts.SERVER_GROUP = args[++index];
                        } else if ("-min_threads".equals(args[index])) {
                                CacheConsts.SERVER_MIN_THREADS = Integer.parseInt(args[++index]);
                        } else if ("-max_threads".equals(args[index])) {
                                CacheConsts.SERVER_MAX_THREADS = Integer.parseInt(args[++index]);
                        } else if ("-port".equals(args[index])) {
                                CacheConsts.SERVER_PORT = Integer.parseInt(args[++index]);
                        } else {
                                break;
                        }
                }
        }

        public static void main(String... args) {
                System.gc();
                argsInit(args);
                SpringApplication.run(App.class, args);
        }

}
