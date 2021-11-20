package com.ezio;

import com.ezio.pipeline.NetEaseMusicPipeline;
import com.ezio.processor.NetEaseMusicPageProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NetEaseMusicApplication {

	@Autowired
	NetEaseMusicPageProcessor mProcessor;
	@Autowired
	NetEaseMusicPipeline mPipeline;

	@GetMapping("/")
	public String index() {
		new Thread(() -> mProcessor.start(mProcessor, mPipeline)).start();

		return "爬虫开启";
	}

	public static void main(String[] args) {
		SpringApplication.run(NetEaseMusicApplication.class, args);

	}
}
