package com.sam.demo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:remote-servlet.xml"})
public class Start {
	public static void main(String[] args) {
		
		SpringApplication.run(Start.class, args);
		
	}
}
