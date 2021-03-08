package com.abc.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
//这里是扫描dao接口的包用于识别mybatis
@MapperScan("com.abc.demo.mapper")
//开启定时任务
@EnableScheduling
//扫描自定义servlet,filter
//@ServletComponentScan
public class SpringbootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoApplication.class, args);
	}
}
