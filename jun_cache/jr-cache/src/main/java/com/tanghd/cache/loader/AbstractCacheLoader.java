package com.tanghd.cache.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.tanghd.cache.builder.CacheBuilder;
import com.tanghd.cache.serialize.Serializer;

public abstract class AbstractCacheLoader implements CacheLoader {
    protected static final Logger log = LoggerFactory.getLogger(AbstractCacheLoader.class);
    protected Serializer serializer;

    protected ExecutorService loadThreads = new ThreadPoolExecutor(0, 200, 1L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    protected AsyncEventBus asyncEventBus;

    public AbstractCacheLoader() {
        asyncEventBus = new AsyncEventBus(loadThreads);
        asyncEventBus.register(this);
    }

    protected <T> T loadData(String key, String cacheKey, CacheBuilder<T> builder) throws Exception {
        try {
            T ret = builder.asyncBuild(key);
            saveData(cacheKey, builder, ret);
            return ret;
        } catch (Exception e) {
            log.error("get content for key:" + key + ", error:", e);
            throw e;
        }
    }

    public abstract <T> void saveData(String cacheKey, CacheBuilder<T> builder, T result) throws Exception;

    public abstract <T> boolean needRefresh(String key, int expire, boolean autoRefresh);

    @Subscribe
    public void listenRefreshMessage(RefreshEventMessage message) {
        try {
            refresh(message.getKey(), message.getCacheBuilder());
        } catch (Exception e) {
            log.error("auto refresh error :", e);
        }
    }

    public static class RefreshEventMessage {
        private String key;
        private CacheBuilder<?> cacheBuilder;

        public RefreshEventMessage(String key, CacheBuilder<?> cacheBuilder) {
            super();
            this.key = key;
            this.cacheBuilder = cacheBuilder;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public CacheBuilder<?> getCacheBuilder() {
            return cacheBuilder;
        }

        public void setCacheBuilder(CacheBuilder<?> cacheBuilder) {
            this.cacheBuilder = cacheBuilder;
        }

    }

    public Serializer getSerializer() {
        return serializer;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

}
