package com.jun.plugin.jpa.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Wujun
 *
 */
public abstract class RedisBaseService<K, V> {

    // @Resource(name = "redisTemplate")
    protected RedisTemplate<K, V> redisTemplate;

}