package com.itstyle.dubbo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.itstyle.dubbo.domain.User;
import com.itstyle.dubbo.service.IUserService;

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
