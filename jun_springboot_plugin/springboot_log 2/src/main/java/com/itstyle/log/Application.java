package com.itstyle.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
/**
 * 创建者  https://blog.52itstyle.com
 * 创建时间	2017年7月24日
 */
@EnableAutoConfiguration
public class Application  {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		  while(true){
			logger.info("普通日志");
			logger.error("错误日志");
		  }
	}
}