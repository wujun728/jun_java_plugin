package org.itkk.udf.cache.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itkk.udf.core.ApplicationConfig;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * CacheRedisConfig
 */
@EnableCaching
@Configuration
public class CacheRedisConfig {

    /**
     * DEFAULT_EXPIRATION
     */
    public static final long DEFAULT_EXPIRATION = 60;

    /**
     * cacheManager
     *
     * @param applicationConfig    applicationConfig
     * @param cacheRedisProperties cacheRedisProperties
     * @param redisTemplate        redisTemplate
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(ApplicationConfig applicationConfig, CacheRedisProperties cacheRedisProperties, RedisTemplate<Object, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setTransactionAware(true);
        cacheManager.setUsePrefix(cacheRedisProperties.getUsePrefix());
        cacheManager.setCachePrefix(new DefRedisCachePrefix(cacheRedisProperties.getPrefix()));
        cacheManager.setDefaultExpiration(cacheRedisProperties.getDefaultExpiration());
        cacheManager.setExpires(cacheRedisProperties.getNames());
        return cacheManager;
    }

    /**
     * redisTemplate
     *
     * @param factory factory
     * @param builder builder
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory, Jackson2ObjectMapperBuilder builder) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
