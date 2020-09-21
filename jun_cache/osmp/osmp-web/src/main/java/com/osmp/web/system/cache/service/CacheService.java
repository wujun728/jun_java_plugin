package com.osmp.web.system.cache.service;

import java.util.Map;

import com.osmp.web.system.cache.entity.CacheDefined;

public interface CacheService {

    void open();

    void close();

    String getCacheInfo();

    String getCacheList();

    String getCacheItem(Map<String, String> params);

    String getCacheById(String cacheId);

    String updateCache(CacheDefined cacheDefined);

    String deleteCache(String key);

}
