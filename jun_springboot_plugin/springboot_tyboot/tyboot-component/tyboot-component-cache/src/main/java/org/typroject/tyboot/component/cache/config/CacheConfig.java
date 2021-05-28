package org.typroject.tyboot.component.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.typroject.tyboot.component.cache.Redis;

import javax.annotation.Resource;

/**
 * Created by yaohelang on 2018/7/1.
 */
@Configuration
public class CacheConfig {

    @Resource
    public RedisTemplate redisTemplate( RedisTemplate<String, Object> redisTemplate)
    {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        Redis.redisTemplate = redisTemplate;
        return redisTemplate;
    }
}
