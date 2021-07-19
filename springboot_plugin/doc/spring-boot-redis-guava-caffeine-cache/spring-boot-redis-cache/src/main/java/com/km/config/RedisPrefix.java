package com.km.config;

import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>redis前缀配置，有时候多个工程共用一个db需要区分</p>
 * Created by zhezhiyong@163.com on 2017/9/22.
 */
public class RedisPrefix implements RedisCachePrefix {
    private final RedisSerializer serializer;
    private final String delimiter;

    public RedisPrefix() {
        this(":");
    }

    public RedisPrefix(String delimiter) {
        this.serializer = new StringRedisSerializer();
        this.delimiter = delimiter;
    }

    @Override
    public byte[] prefix(String cacheName) {
        return this.serializer.serialize(this.delimiter != null ? this.delimiter.concat(":").concat(cacheName).concat(":") : cacheName.concat(":"));
    }
}
