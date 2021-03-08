package com.gosalelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAspectCache
public class AspectCacheTest {
    public static void main(String args[]) {
        new SpringApplication(AspectCacheTest.class).run(args);
    }
}
