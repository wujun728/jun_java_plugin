package com.sso.cache;

import java.util.TimerTask;

import com.sso.cache.strategy.CacheStrategy;

/**
 * 缓存清除任务
 */
public class CacheEvictTask extends TimerTask {
    private final CacheStrategy cacheStrategy; //缓存策略
    private final Object key; //要清除的键

    public CacheEvictTask(CacheStrategy cacheStrategy, Object key) {
        this.cacheStrategy = cacheStrategy;
        this.key = key;
    }

    @Override
    public void run() {
        cacheStrategy.evict(key);
    }
}
