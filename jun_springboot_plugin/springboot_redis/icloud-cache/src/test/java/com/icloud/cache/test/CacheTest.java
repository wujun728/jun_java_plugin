package com.icloud.cache.test;
import com.icloud.cache.EnableiCloudCache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @FileName CacheTest.java
 * @Description: 
 * 
 * @date Nov 15, 2018 11:11:39 AM
 * @author Wujun
 * @version 1.0
 */
@SpringBootApplication
@EnableiCloudCache
public class CacheTest {
    public static void main(String args[]) {
        SpringApplication app = new SpringApplication(CacheTest.class);
        app.run(args);
    }

}
