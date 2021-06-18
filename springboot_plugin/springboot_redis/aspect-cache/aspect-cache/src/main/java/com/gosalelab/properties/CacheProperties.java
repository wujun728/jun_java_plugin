package com.gosalelab.properties;

import com.gosalelab.constants.CacheConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 */
@Component
@ConfigurationProperties(prefix = CacheConstants.ASPECT_CACHE_PREFIX)
public class CacheProperties {

    /**
     * ehcahe config
     */
    @NestedConfigurationProperty
    private EHCacheProperties ehcache = new EHCacheProperties();

    /**
     * redis config
     */
    @NestedConfigurationProperty
    private RedisCacheProperties redisCache = new RedisCacheProperties();
    /**
     * enable cache
     */
    private boolean enable = false;
    /**
     * cache provider
     */
    private String provider = CacheConstants.DEFAULT_CACHE_PROVIDER;

    /**
     * default cache time，default value is 3600 seconds(half hour)
     */
    private Integer expireTime = 3600;

    /**
     * key generator
     */
    private String keyGenerator = CacheConstants.DEFAULT_CACHE_KEY_GENERATE_NAME;

    public EHCacheProperties getEhcache() {
        return ehcache;
    }

    public void setEhcache(EHCacheProperties ehcache) {
        this.ehcache = ehcache;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getKeyGenerator() {
        return keyGenerator;
    }

    public void setKeyGenerator(String keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public RedisCacheProperties getRedisCache() {
        return redisCache;
    }

    public void setRedisCache(RedisCacheProperties redisCache) {
        this.redisCache = redisCache;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }
}
