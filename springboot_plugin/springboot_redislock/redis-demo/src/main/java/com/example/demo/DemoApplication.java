package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DemoApplication implements CommandLineRunner {

	@Value("${server.port:8833}")
	private String serverPort;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Application is success, Index >> http://127.0.0.1:{}", serverPort);
		log.info("API{初始化库存数量} >> http://127.0.0.1:{}/api/spike/initSku", serverPort);
		log.info("API{减少库存数量} >> http://127.0.0.1:{}/api/spike/reduceSku", serverPort);
		log.info("API{减少库存数量(加事务)} >> http://127.0.0.1:{}/api/spike/reduceSku2", serverPort);
		log.info("API{查看共减少库存数量} >> http://127.0.0.1:{}/api/spike/successNum", serverPort);

		log.info("API{查看共减少库存数量} >> http://127.0.0.1:{}/api/set/successNum", serverPort);
		log.info("API{减少库存数量} >> http://127.0.0.1:{}/api/set/reduceSku", serverPort);
	}
}
