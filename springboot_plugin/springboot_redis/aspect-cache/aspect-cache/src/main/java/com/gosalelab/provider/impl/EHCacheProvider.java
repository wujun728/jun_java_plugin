package com.gosalelab.provider.impl;

import com.alibaba.fastjson.JSON;
import com.gosalelab.constants.CacheConstants;
import com.gosalelab.provider.AbstractCacheProvider;
import com.gosalelab.provider.CacheProvider;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component("ehCacheProvider")
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "provider", havingValue = CacheConstants.CACHE_EHCACHE_NAME)
public class EHCacheProvider extends AbstractCacheProvider implements CacheProvider {

    private static Logger logger = LoggerFactory.getLogger(EHCacheProvider.class);

    private final CacheManager cacheManager;

    @Autowired
    public EHCacheProvider(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void put(String key, Object value, Integer expire) {
        try {
            String valueStr = JSON.toJSONString(value);
            getCache().put(key, valueStr);
        } catch (Exception e) {
            logger.error("cache put fail, key: " + key + ",value: " + value + ",expire: " + expire, e);
        }
    }

    @Override
    public Boolean del(String key) {
        try {
            getCache().remove(key);
        } catch (Exception e) {
            logger.error("cache put fail, key: " + key, e);
            return false;
        }

        return true;
    }

    @Override
    public Object get(String key, Type returnType) {
        try {
            String cacheValue = getCache().get(key);
            return handleReturnValue(cacheValue, returnType);
        } catch (Exception e) {
            logger.error("cache get fail,key: " + key, e);
            return null;
        }
    }

    private Cache<String, String> getCache() {
        return cacheManager.getCache(cacheProperties.getEhcache().getDefaultCacheName(), String.class, String.class);
    }

}
