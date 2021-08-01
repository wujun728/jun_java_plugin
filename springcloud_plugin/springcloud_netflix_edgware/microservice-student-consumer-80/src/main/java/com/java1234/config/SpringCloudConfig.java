package com.java1234.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * SpringCloud相关配置
 * @author Administrator
 *
 */
@Configuration
public class SpringCloudConfig {

	/**
	 * 调用服务模版对象
	 * @return
	 */
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
}
