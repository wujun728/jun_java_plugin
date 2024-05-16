package com.cailei.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：蔡磊
 * @date ：2022/10/20 15:12
 * @description：启动类
 */
@SpringBootApplication
//@MapperScan("com.cailei.demo.mapper") 移至了com/cailei/demo/config/MybatisPlusConfig.java
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}

