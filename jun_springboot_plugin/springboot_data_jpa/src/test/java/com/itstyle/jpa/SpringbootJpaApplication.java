package com.itstyle.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itstyle.jpa.model.User;
import com.itstyle.jpa.repository.UserRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			User user = new User();
			user.setName("张三");
			user.setAge(20);
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}