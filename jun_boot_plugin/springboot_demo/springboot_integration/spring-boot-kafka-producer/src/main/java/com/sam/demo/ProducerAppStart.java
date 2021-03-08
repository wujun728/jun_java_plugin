package com.sam.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProducerAppStart  {

	public static void main(String[] args) throws InterruptedException {

		ApplicationContext app = SpringApplication.run(ProducerAppStart.class, args);
		
		Sender sender = app.getBean(Sender.class);
		sender.sendMessage();

		while(true){
			sender.sendMessage();
			Thread.sleep(200);
		}
		
	}
	
}
