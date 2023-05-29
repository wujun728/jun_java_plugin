package com.jun.plugin.mongodb2;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
/**
 * 启动类
 * 创建者 科帮网
 * 创建时间	2017年7月19日
 *
 */
@EnableAutoConfiguration
@Log
@ComponentScan(basePackages={"com.itstyle.mongodb"})
public class Application  {
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		log.info("项目启动 ");
	}
}