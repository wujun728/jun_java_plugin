package com.buxiaoxia.business;

import com.buxiaoxia.business.aopDemo.service.HelloService;
import com.buxiaoxia.enableDemo.annotation.EnableCustomAnnotation;
import com.buxiaoxia.enableDemo.bean.Demo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 此处包扫描的范围是：com.buxiaoxia.business.*
 * <p>
 * Created by xw on 2017/2/20.
 * 2017-02-20 16:51
 */
@Slf4j
@EnableCustomAnnotation
@SpringBootApplication
@ComponentScan("com.buxiaoxia.business.*")
public class Application implements CommandLineRunner {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class);
	}

	@Autowired
	private Demo demo;

	@Autowired
	private HelloService helloService;

	@Override
	public void run(String... strings) throws Exception {
		log.info("===============样例一使用样例测试开始===================");
		log.info("扫描的包范围是：com.buxiaoxia.business.*");
		log.info("获取应用没有扫描到的配置注入的bean实例，内容为：{}", demo.getHello());
		log.info("-----------");
		log.info("-----------");
		log.error("如果去除@EnableCustomAnnotation配置，服务会报错，提示：Field demo in " +
				"com.buxiaoxia.business.Application required a bean of type " +
				"'com.buxiaoxia.enableDemo.bean.Demo' that could not be found.");
		log.info("===============样例一使用样例测试结束===================");
		log.info("");
		log.info("");
		log.info("");
		log.info("===============样例二使用样例测试开始===================");
		helloService.getUser("海贼王");
		log.info("===============样例二使用样例测试结束===================");
		System.exit(1);
	}

}


