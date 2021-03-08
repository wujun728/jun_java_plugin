package com.monkeyk.sos.infrastructure;

import com.monkeyk.sos.web.context.BeanProvider;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

/**
 * 2018/10/14
 * <p>
 * <p>
 * Cache 操作相关
 *
 * @author Wujun
 * @since 1.0
 */
public class SOSCacheUtils implements CacheConstants {


    private SOSCacheUtils() {
    }


    /**
     * 用户 Cache
     *
     * @return Cache instance
     */
    public static Cache userCache() {
        final CacheManager cacheManager = getCacheManager();
        return cacheManager.getCache(USER_CACHE);
    }

    private static CacheManager getCacheManager() {
        final CacheManager cacheManager = BeanProvider.getBean(CacheManager.class);
        Assert.notNull(cacheManager, "cacheManager is null");
        return cacheManager;
    }


}
