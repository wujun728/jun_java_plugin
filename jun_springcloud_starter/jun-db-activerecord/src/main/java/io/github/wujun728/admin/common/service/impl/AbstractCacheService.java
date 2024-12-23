package io.github.wujun728.admin.common.service.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.StrUtil;
import io.github.wujun728.admin.common.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractCacheService<T> implements CacheService<T> {
    private Cache<String, T> cache;
    private static Map<String,Cache> allCaches = new HashMap<>();
    private String CacheName = getClass().getSimpleName();
    {
        cache = initCache();
        allCaches.put(CacheName,cache);
    }
    protected Cache<String, T> initCache(){
        return CacheUtil.newLFUCache(1000);
    }

    protected abstract T load(String key);

    protected boolean isNull(T value){
        return value == null;
    }
    protected String getPrefix(){
        return "";
    }
    protected String getFullKey(String key){
        return StrUtil.format("{}{}",getPrefix(),key);
    }

    @Override
    public T get(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        String fullKey = getFullKey(key);
        T value = cache.get(fullKey);
        if(isNull(value)){
            synchronized (fullKey.intern()){
                value = cache.get(fullKey);
                if(!isNull(value)){
                    return value;
                }
                value = load(key);
                if(!isNull(value)){
                    log.info("重建缓存:{},{}",CacheName,fullKey);
                    cache.put(fullKey,value);
                }
                return value;
            }
        }
        log.info("命中缓存:{},{}",CacheName,fullKey);
        return value;
    }

    @Override
    public void invalid(String key) {
        if(StringUtils.isBlank(key)){
            return;
        }
        String fullKey = getFullKey(key);
        cache.remove(fullKey);
        log.info("清理缓存:{},{}",CacheName,fullKey);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    public static void clearAll(){
        for (Map.Entry<String, Cache> en : allCaches.entrySet()) {
            log.info("清理全部缓存{}",en.getKey());
            en.getValue().clear();
        }
    }
}
