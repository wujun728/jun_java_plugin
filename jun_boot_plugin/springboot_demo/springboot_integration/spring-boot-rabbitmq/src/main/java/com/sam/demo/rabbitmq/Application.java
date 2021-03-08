package com.sam.demo.rabbitmq;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.sam.demo.rabbitmq.sender.Sender;

@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(Application.class, args);
        Sender sender = app.getBean(Sender.class);
        while(true){
    		String msg = UUID.randomUUID().toString();
    		sender.send(msg);
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}