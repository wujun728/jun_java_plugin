package com.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description StartApplication
 * @Author template
 * @Date 2023/3/14 15:27
 */
@SpringBootApplication
public class StartApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(StartApplication.class, args);
        System.out.println("代码生成器启动成功！");
    }
}
