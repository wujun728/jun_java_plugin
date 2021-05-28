package com.itstyle.jpa;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 启动类
 * 创建者 科帮网
 * 创建时间	2017年7月26日
 * API接口测试：http://localhost:8080/jpa/swagger-ui.html
 */
@SpringBootApplication
public class Application  {
	private static final Logger logger = Logger.getLogger(Application.class);
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		logger.info("项目启动 ");
	}
}