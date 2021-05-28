package net.oschina.j2cache.springcache;

import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import net.oschina.j2cache.util.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.cache.Cache;

import java.io.IOException;

public class TestJ2CacheSpringCacheManageAdapter {

    @Test
    public void testCache() throws IOException {

        SerializationUtils.init("fst", null);

        J2CacheConfig config = J2CacheConfig.initFromConfig("/j2cache.properties");
        J2CacheBuilder j2CacheBuilder = J2CacheBuilder.init(config);

        J2CacheSpringCacheManageAdapter cacheManageAdapter = new J2CacheSpringCacheManageAdapter(j2CacheBuilder, true);
        String cacheName = "ddd";
        Cache cache = cacheManageAdapter.getCache(cacheName);

        String key = "dddd";
        Cache.ValueWrapper valueWrapper = cache.get(key);

        Assert.assertNull(valueWrapper); // 第一次取  是null

        cache.put(key, null); // 存 null


        valueWrapper = cache.get(key);
        Assert.assertNull(valueWrapper); //允许存 null, 存 null 之后再取  是 null
        cache.evict(key); // 失效
        valueWrapper = cache.get(key);
        Assert.assertNull(valueWrapper); // 失效后再取  是null
    }
}
