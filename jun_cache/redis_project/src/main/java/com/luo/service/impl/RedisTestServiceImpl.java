package com.luo.service.impl;

import org.springframework.stereotype.Service;
import com.luo.service.RedisTestService;

@Service
public class RedisTestServiceImpl implements RedisTestService {
	
	public String getTimestamp(String param) {
		Long timestamp = System.currentTimeMillis();
		return timestamp.toString();
	}

}
