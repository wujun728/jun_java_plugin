package com.flying.cattle.wf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WfRedisApplication {
	@Autowired
	
	public static void main(String[] args) {
		SpringApplication.run(WfRedisApplication.class, args);
	}

}
