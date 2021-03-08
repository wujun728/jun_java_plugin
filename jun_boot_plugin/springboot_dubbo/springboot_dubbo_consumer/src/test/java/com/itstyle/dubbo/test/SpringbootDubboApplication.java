package com.itstyle.dubbo.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itstyle.dubbo.domain.User;
import com.itstyle.dubbo.service.IUserService;

@ImportResource({"classpath:dubbo.xml"})
@SpringBootApplication
//此处 userService 报空指针 如果是注解方式
public class SpringbootDubboApplication implements CommandLineRunner {

	@Reference
	private IUserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("开启");
		User user = new User("张三", 19);
		userService.saveUser(user);
	}
}
