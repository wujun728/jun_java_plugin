package com.jun.plugin.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jun.plugin.common.cache.IGatRedisKey;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * @ClassName: DcsClientRunner
 * @Description:
 * @author: renkai721
 * @date: 2018年7月5日 下午2:06:21
 */
@Component
@Slf4j
@Order(1)
public class DcsClientRunner  implements CommandLineRunner, IGatRedisKey {
	@Autowired
	public RestTemplate restTemplate;

	@Override
	public void run(String... strings) throws Exception {
		
	}
	
  
}
