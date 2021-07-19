package com.buxiaoxia;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by xw on 2017/2/20.
 * 2017-02-20 16:51
 */
@Slf4j
@EnableCaching  // 启动
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("1-消息发送至“chat”主题，内容为:\"{}\"", "Hello from Redis!");
		stringRedisTemplate.convertAndSend("chat", "Hello from Redis!");
		log.info("2-消息再次发送至“chat”主题，内容为:\"{}\"", "你好，redis!");
		stringRedisTemplate.convertAndSend("chat", "你好，redis!");
	}
}
