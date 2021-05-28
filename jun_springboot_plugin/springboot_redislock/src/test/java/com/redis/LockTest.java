package com.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.lock.RedisLock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName LockTest
 * @Description 测试类
 * @author Wujun
 * @Date 2019/7/5 9:33
 * @Version 1.0
 */
public class LockTest {
	RedisTemplate<String, Object> template;

	@Before
	public void redis() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("120.27.213.73");
		redisStandaloneConfiguration.setPort(6379);
		redisStandaloneConfiguration.setDatabase(15);
		RedisPassword redisPassword = RedisPassword.of("frgrgrgskl34gkk");
		redisStandaloneConfiguration.setPassword(redisPassword);
		RedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
		template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
	}

	@Test
	public void lock() {
		RedisLock redisLock = RedisLock.create("test", template);
		try {
			boolean result = redisLock.tryLock(1000, TimeUnit.SECONDS);
			redisLock.unlock();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
