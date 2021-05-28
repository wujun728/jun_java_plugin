package com.tanghd.cache.builder;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCacheBuilder<T> implements CacheBuilder<T> {

    protected static final Logger CACHE_BUILDER_LOG = LoggerFactory.getLogger(AbstractCacheBuilder.class);
    protected static final ExecutorService asyncService = Executors.newCachedThreadPool();

    protected int expireSeconds = 30;
    protected boolean autoRefresh = false;
    protected String keyPrefix = "";
    protected volatile boolean loadingMutex = false;

    protected ReentrantLock loadingLock = new ReentrantLock();
    protected Map<String, Future<T>> keyLoadingTask = new ConcurrentHashMap<>();

    public class AsyncBuilder implements Callable<T> {

        private String key;

        public AsyncBuilder(String key) {
            this.key = key;
        }

        @Override
        public T call() throws Exception {
            T obj = null;
            try {
                obj = build(key);
            } finally {
                loadingLock.lock();
                try {
                    keyLoadingTask.remove(key);
                } finally {
                    loadingLock.unlock();
                }
            }
            return obj;
        }

    }

    @Override
    public T asyncBuild(String key) throws Exception {
        if (this.loadingMutex) {
            boolean asyncSuccess = false;
            Future<T> future = keyLoadingTask.get(key);
            if (null == future) {
                loadingLock.lock();
                try {
                    future = keyLoadingTask.get(key);
                    if (null == future) {
                        try {
                            future = asyncService.submit(new AsyncBuilder(key));
                            asyncSuccess = true;
                        } catch (Exception e) {
                            future = null;
                        }
                        if (asyncSuccess && null != future) {
                            keyLoadingTask.put(key, future);
                        }
                    }
                } finally {
                    loadingLock.unlock();
                }
            } else {
                return future.get();
            }
            // 异步线程提交线程池处理失败，直接返回结果
            if (!asyncSuccess && null == future) {
                return build(key);
            }
            return future.get();
        } else {
            return build(key);
        }
    }

    @Override
    public T build(String key) throws Exception {
        if (CACHE_BUILDER_LOG.isDebugEnabled()) {
            CACHE_BUILDER_LOG.debug("load data for key:" + key);
        }
        T ret = buildIntern(key);
        return ret;
    }

    public abstract T buildIntern(String key) throws Exception;

    @Override
    public CacheBuilder<T> setLoadingMutex(boolean loadingMutex) {
        this.loadingMutex = loadingMutex;
        return this;
    }

    @Override
    public CacheBuilder<T> setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
        return this;
    }

    @Override
    public CacheBuilder<T> setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
        return this;
    }

    @Override
    public CacheBuilder<T> setKeyPrefix(String keyPrefix) {
        if (null == keyPrefix) {
            keyPrefix = "";
        } else {
            this.keyPrefix = keyPrefix;
        }
        return this;
    }

    @Override
    public boolean getLoadingMutex() {
        return this.loadingMutex;
    }

    @Override
    public int getExpireSeconds() {
        return this.expireSeconds;
    }

    @Override
    public boolean getAutoRefresh() {
        return this.autoRefresh;
    }

    @Override
    public String getKeyPrefix() {
        return this.keyPrefix;
    }

}
