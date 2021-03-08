package com.leo.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 如果需要连接公有云上的redis服务、则必须加这个配置。
 * @author Wujun
 *
 */
@Configuration  
@EnableRedisHttpSession  
public class RedisSessionConfig {  
	
	@Bean
	public static ConfigureRedisAction configureRedisAction() {
	    return ConfigureRedisAction.NO_OP;
	}
}  
