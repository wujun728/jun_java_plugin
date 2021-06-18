package com.gosalelab.configs;


import com.gosalelab.constants.CacheConstants;
import com.gosalelab.properties.CacheProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Wujun
 */
@Configuration
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "provider", havingValue = CacheConstants.CACHE_REDIS_NAME)
public class RedisCacheConfig {

    private final CacheProperties properties;

    @Autowired
    public RedisCacheConfig(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    JedisPool jedisPool() {
        if (StringUtils.isEmpty(properties.getRedisCache().getPassword())) {
            return new JedisPool(jedisPoolConfig(),
                    properties.getRedisCache().getHost(),
                    properties.getRedisCache().getPort(),
                    properties.getRedisCache().getTimeout());
        }

        return new JedisPool(jedisPoolConfig(), properties.getRedisCache().getHost(),
                properties.getRedisCache().getPort(),
                properties.getRedisCache().getTimeout(),
                properties.getRedisCache().getPassword(),
                properties.getRedisCache().getDatabase());
    }

    /**
     * redis pool config
     * @return pool config
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(properties.getRedisCache().getMaxTotal());
        config.setMaxIdle(properties.getRedisCache().getMaxIdle());
        config.setMinIdle(properties.getRedisCache().getMinIdle());
        config.setMaxWaitMillis(properties.getRedisCache().getMaxWaitMillis());
        return config;
    }
}
