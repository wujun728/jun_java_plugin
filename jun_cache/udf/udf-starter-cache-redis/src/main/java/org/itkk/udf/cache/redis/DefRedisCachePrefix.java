package org.itkk.udf.cache.redis;

import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * DefRedisCachePrefix
 */
public class DefRedisCachePrefix implements RedisCachePrefix {
    /**
     * serializer
     */
    private final RedisSerializer serializer = new StringRedisSerializer();

    /**
     * prefix
     */
    private final String prefix;

    /**
     * 构造函数
     *
     * @param prefix 前缀
     */
    public DefRedisCachePrefix(String prefix) {
        this.prefix = prefix;
    }


    @Override
    public byte[] prefix(String cacheName) {
        return serializer.serialize(prefix.concat(":"));
    }
}
