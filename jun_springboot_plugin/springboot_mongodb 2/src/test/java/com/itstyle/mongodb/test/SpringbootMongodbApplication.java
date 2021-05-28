package com.itstyle.mongodb.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.itstyle.mongodb.service.IUserService;
import com.itstyle.mongodb.service.UserRepository;

@SpringBootApplication
@ComponentScan(basePackages={"com.itstyle.mongodb"})
public class SpringbootMongodbApplication implements CommandLineRunner {

	@Autowired
	private IUserService userService;
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			userRepository.removeUser("小明");
			/*Users users = new Users("1", "小明", 10);
			users.setAddress("青岛市");
			userService.saveUser(users);
			List<Users> list = userService.listUser();
			System.out.println("一共这么多人:"+list.size());*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}