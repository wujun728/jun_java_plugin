package com.baomidou.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * springCacheManager对原生shiroCacheManager的包装
 *
 * @author Wujun
 */
public class SpringCacheManager implements CacheManager {

  private org.springframework.cache.CacheManager cacheManager;

  public SpringCacheManager(org.springframework.cache.CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @Override
  public <K, V> Cache<K, V> getCache(String name) throws CacheException {
    return new SpringCache<>(cacheManager.getCache(name));
  }

}
