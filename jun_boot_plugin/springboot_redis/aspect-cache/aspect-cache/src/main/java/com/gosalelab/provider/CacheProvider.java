package com.gosalelab.provider;

import java.lang.reflect.Type;

/**
 * @author Wujun
 */
public interface CacheProvider {
    /**
     * put the value with key into cache
     * @param key cache key
     * @param value json
     * @param expire expire time
     */
    void put(String key, Object value, Integer expire);

    /**
     * get the value with key
     * @param key cache key
     * @param returnType type of the return value
     * @return cache value
     */
    Object get(String key, Type returnType);

    /**
     * delete value with the key
     * @param key cache key
     * @return if the cache is deleted
     */
    Boolean del(String key);
}
