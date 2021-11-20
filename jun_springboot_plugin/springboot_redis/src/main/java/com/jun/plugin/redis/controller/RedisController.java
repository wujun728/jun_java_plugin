package com.jun.plugin.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.redis.service.RedisService;

import java.util.Set;

@RestController
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	RedisService redisService;

	@GetMapping("/hi/{name}")
	public String hi(@PathVariable String name) {
		redisTemplate.opsForValue().set("a", name);
		return "successful";
	}

	@GetMapping("/hi")
	public String getRedis() {
		return redisTemplate.opsForValue().get("a");
	}

	@GetMapping("/initZset")
	public Set initZset() {
		return redisService.initZset();
	}

	@GetMapping("/test")
	public void testTransaction() {
		redisService.testTransaction();
	}

}
