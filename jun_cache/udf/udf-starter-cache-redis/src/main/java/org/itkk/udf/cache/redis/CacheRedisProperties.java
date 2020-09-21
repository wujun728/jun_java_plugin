package org.itkk.udf.cache.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * CacheRedisProperties
 */
@Component
@Data
@ConfigurationProperties(prefix = "org.itkk.cache.redis.properties")
public class CacheRedisProperties {

    /**
     * 是否使用前缀
     */
    private Boolean usePrefix = true;

    /**
     * 描述 : 缓存前缀
     */
    private String prefix;

    /**
     * 默认的过期时间
     */
    private Long defaultExpiration = CacheRedisConfig.DEFAULT_EXPIRATION;

    /**
     * 描述 : 缓存名称(规则: key:缓存名称,value:缓存过期时间)
     */
    private Map<String, Long> names;
}
