package com.itstyle.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringbootLoglApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(SpringbootLoglApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringbootLoglApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("哈哈哈");
	}
}
