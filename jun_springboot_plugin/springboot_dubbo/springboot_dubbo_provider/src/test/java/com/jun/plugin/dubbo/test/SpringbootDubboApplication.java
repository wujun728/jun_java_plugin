package com.jun.plugin.dubbo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.jun.plugin.dubbo2.domain.User;
import com.jun.plugin.dubbo2.service.IUserService;

@SpringBootApplication
//@ComponentScan(basePackages = { "com.itstyle.dubbo" })
@ImportResource({"classpath:dubbo.xml"})
public class SpringbootDubboApplication implements CommandLineRunner {

	@Autowired
	private IUserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User("张三", 19);
		userService.saveUser(user);
	}
}
