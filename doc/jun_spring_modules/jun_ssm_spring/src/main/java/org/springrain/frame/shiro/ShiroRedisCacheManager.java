package org.springrain.frame.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springrain.frame.cache.ICache;
/**
 * redis的缓存管理器
 * @author caomei
 *
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {
	private ICache cache;
	@Override
	protected Cache createCache(String cacheName) throws CacheException {
		return new ShiroRedisCache<String, Object>(cacheName,cache);
	}
	public ICache getCache() {
		return cache;
	}
	public void setCache(ICache cached) {
		this.cache = cached;
	}

}
