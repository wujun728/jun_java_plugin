package com.flying.cattle.wf.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	/**
	 * ReactiveRedisTemplate 注入（此配置来源官方）
	 * 
	 * @param factory
	 * @return
	 */
//	@Bean
//	public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
//		ReactiveRedisTemplate<String, String> reactiveRedisTemplate = new ReactiveRedisTemplate<>(factory,
//				RedisSerializationContext.string());
//		return reactiveRedisTemplate;
//	}

	@Bean
	@Qualifier("reactiveRedisTemplate")
	public ReactiveRedisTemplate<String, Object> stringReactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
		RedisSerializer<String> keySerializer = new StringRedisSerializer();
		RedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext.<String, Object>newSerializationContext()
				.key(keySerializer).value(valueSerializer)
				.hashKey(keySerializer).hashValue(valueSerializer).build();
		return new ReactiveRedisTemplate<String, Object>(reactiveRedisConnectionFactory, serializationContext);
	}
}