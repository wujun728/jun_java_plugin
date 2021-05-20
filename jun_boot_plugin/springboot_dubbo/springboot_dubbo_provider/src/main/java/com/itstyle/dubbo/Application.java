package com.itstyle.dubbo;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
/**
 * @author Wujun
 */
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.itstyle.web"})
@ImportResource({"classpath:dubbo.xml"})
public class Application  {
	private static final Logger logger = Logger.getLogger(Application.class);
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		logger.info("项目启动 ");
	}
}