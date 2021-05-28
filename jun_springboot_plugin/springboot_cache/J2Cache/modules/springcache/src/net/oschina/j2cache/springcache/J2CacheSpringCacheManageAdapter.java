package net.oschina.j2cache.springcache;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.J2CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import java.util.Collection;

/**
 * @author Wujun
 */
public class J2CacheSpringCacheManageAdapter extends AbstractTransactionSupportingCacheManager {
    /**
     * Load the initial caches for this cache manager.
     * <p>Called by {@link #afterPropertiesSet()} on startup.
     * The returned collection may be empty but must not be {@code null}.
     */
    @Override
    protected Collection<? extends Cache> loadCaches() {
        return null;
    }

    private J2CacheBuilder j2CacheBuilder;

    private boolean allowNullValues = true;

    /**
     * @param j2CacheBuilder  可选参数，不传则用  J2Cache.getChannel() 获取 CacheChannel
     * @param allowNullValues 默认 true
     */
    public J2CacheSpringCacheManageAdapter(J2CacheBuilder j2CacheBuilder, boolean allowNullValues) {
        this.j2CacheBuilder = j2CacheBuilder;
        this.allowNullValues = allowNullValues;
    }

    public J2CacheSpringCacheManageAdapter() {
    }

    public J2CacheSpringCacheManageAdapter(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    public J2CacheSpringCacheManageAdapter(J2CacheBuilder j2CacheBuilder) {
        this.j2CacheBuilder = j2CacheBuilder;
    }


    @Override
    protected Cache getMissingCache(String name) {
        CacheChannel cacheChannel = j2CacheBuilder == null ? J2Cache.getChannel() : j2CacheBuilder.getChannel();
        return new J2CacheSpringCacheAdapter(allowNullValues, cacheChannel, name);
    }
}
