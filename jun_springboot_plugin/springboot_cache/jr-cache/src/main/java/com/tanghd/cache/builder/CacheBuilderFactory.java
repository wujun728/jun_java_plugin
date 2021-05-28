package com.tanghd.cache.builder;

public class CacheBuilderFactory<T> {

    private boolean autoRefresh;
    private String keyPrefix;
    private int expireSeconds;
    private boolean loadingMutex;

    private CacheBuilderFactory() {
        autoRefresh = false;
        keyPrefix = "Cache_" + System.currentTimeMillis() + "_";
        expireSeconds = 30;
        loadingMutex = false;
    }

    public static CacheBuilderFactory<Object> newBuilder() {
        return new CacheBuilderFactory<Object>();
    }

    public CacheBuilderFactory<T> keyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
        return this;
    }

    public CacheBuilderFactory<T> expireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
        return this;
    }

    public CacheBuilderFactory<T> autoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
        return this;
    }

    public CacheBuilderFactory<T> loadingMutex(boolean loadingMutex) {
        this.loadingMutex = loadingMutex;
        return this;
    }

    public <T1 extends T> CacheBuilder<T1> build(CacheBuilder<T1> cacheBuilder) {
        cacheBuilder.setAutoRefresh(this.autoRefresh).setExpireSeconds(this.expireSeconds).setKeyPrefix(this.keyPrefix)
                .setLoadingMutex(loadingMutex);
        return cacheBuilder;
    }

}
