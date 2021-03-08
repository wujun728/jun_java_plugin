package com.tanghd.cache.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import redis.clients.jedis.JedisPool;

import com.tanghd.cache.builder.AbstractCacheBuilder;
import com.tanghd.cache.builder.CacheBuilder;
import com.tanghd.cache.builder.CacheBuilderFactory;
import com.tanghd.cache.loader.RedisCacheLoader;
import com.tanghd.cache.serialize.JsonSerializer;
import com.tanghd.cache.util.RedisUtil;

public class TestCache {

    private RedisUtil redisUtil;
    private RedisCacheLoader cacheLoader;

    private CacheBuilder<Long> cacheBuilder = CacheBuilderFactory.newBuilder().autoRefresh(true).expireSeconds(30)
            .keyPrefix("Test").loadingMutex(true).build(new AbstractCacheBuilder<Long>() {

                @Override
                public Long buildIntern(String key) throws Exception {
                    Long tmp = Long.valueOf(key);
                    return tmp + (long) (Math.random() * 100000);
                }

            });

    public TestCache() {
        redisUtil = new RedisUtil();
        JedisPool master = new JedisPool("127.0.0.1", 6379);
        JedisPool slave = new JedisPool("127.0.0.1", 6380);
        redisUtil.setMasterPool(master);
        redisUtil.setSlavePool(slave);
        cacheLoader = new RedisCacheLoader();
        cacheLoader.setRedisClient(redisUtil);
        cacheLoader.setSerializer(new JsonSerializer());
    }

    public Long test(String key) throws Exception {
        return cacheLoader.get(key, Long.class, cacheBuilder);
    }

    public static void main(String[] args) throws Exception {
        final TestCache testCache = new TestCache();
        final CountDownLatch start = new CountDownLatch(1);
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        List<Future<Long>> resultFuture = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            resultFuture.add(threadPool.submit(new Callable<Long>() {

                @Override
                public Long call() throws Exception {
                    start.await();
                    long start = System.nanoTime();
                    for (int i = 0; i < 1000000; i++) {
                        if(testCache.test(String.valueOf(i % 3))==null){
                            return -1L;
                        }
                    }
                    return System.nanoTime() - start;

                }

            }));
            start.countDown();
        }
        for (Future<Long> f : resultFuture) {
            System.out.println(f.get() / 1000000.0);
        }
    }
}
