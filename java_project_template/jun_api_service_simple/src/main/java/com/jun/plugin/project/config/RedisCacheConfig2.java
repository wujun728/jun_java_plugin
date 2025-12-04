package com.jun.plugin.project.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Redis缓存配置。
 */
@Configuration
@EnableCaching
public class RedisCacheConfig2 {

	@Autowired
    private RedisTemplate<String,Object> redisTemplate;

	@Resource
	private RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		// 字符串Key序列化
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		// 对象值序列化
//		ObjectRedisSerializer objectRedisSerializer = new ObjectRedisSerializer();
		RedisSerializer<Object> objectRedisSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setValueSerializer(objectRedisSerializer);
		redisTemplate.setHashValueSerializer(objectRedisSerializer);
		return redisTemplate;
	}

	@Bean(name = "redisCacheManager")
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		
		// 初始化一个RedisCacheWriter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
		// 设置CacheManager的值序列化方式为json序列化
		RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
				.fromSerializer(jsonSerializer);
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
		config.entryTtl(Duration.ofSeconds(60*2)).disableCachingNullValues();// 不缓存空值
//		// 初始化RedisCacheManager
		return new RedisCacheManager(redisCacheWriter, config, getRedisCacheConfigurationMap());
	}
	

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("messagCache", this.getRedisCacheConfigurationWithTtl(30 * 60));   
        //自定义设置缓存时间
        redisCacheConfigurationMap.put("userCache", this.getRedisCacheConfigurationWithTtl(60));
        redisCacheConfigurationMap.put("cache4h", this.getRedisCacheConfigurationWithTtl(60*60*4));
        redisCacheConfigurationMap.put("cache5m", this.getRedisCacheConfigurationWithTtl(60*2));
        redisCacheConfigurationMap.put("cache2m", this.getRedisCacheConfigurationWithTtl(60*2));
        redisCacheConfigurationMap.put("cache1m", this.getRedisCacheConfigurationWithTtl(60));
        
        return redisCacheConfigurationMap;
    }
 
    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
//    	RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
		// 设置CacheManager的值序列化方式为json序列化
		RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
				.fromSerializer(jsonSerializer);
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jsonSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    } 
	 
}
