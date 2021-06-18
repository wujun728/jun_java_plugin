package com.tanghd.cache.builder;


public interface CacheBuilder<T> {
    T build(String key) throws Exception;
    
    T asyncBuild(String key) throws Exception;

    CacheBuilder<T> setExpireSeconds(int expireSeconds);

    CacheBuilder<T> setAutoRefresh(boolean autoRefresh);

    CacheBuilder<T> setKeyPrefix(String keyPrefix);

    CacheBuilder<T> setLoadingMutex(boolean loadingMutex);
    
    int getExpireSeconds();

    boolean getAutoRefresh();

    String getKeyPrefix();
    
    boolean getLoadingMutex();
}
