/**
 * Copyright (c) 2016-~, Bosco.Liao (bosco_liao@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.iherus.shiro.cache.redis;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>缓存管理器</p>
 * <p>Description:管理缓存池的初始化、获取当前缓存对象和销毁缓存池</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月11日-下午4:03:01
 */
public class RedisCacheManager implements CacheManager, Initializable, Destroyable {

	/**
     * Private internal log instance.
     */
	private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
	
	private static final String DEFAULT_CACHE_KEY_PREFIX = "shiro_cache_";

	/**
	 * The cache pool used by this implementation to create caches.
	 */
	protected RedisCachePool cachePool;
	
	/**
	 * Set a Default key prefix for all caches.
	 */
	private String shiroCacheKeyPrefix = DEFAULT_CACHE_KEY_PREFIX;

	/**
	 * The cache pool base on this factory
	 */
	private RedisCacheConfigFactory configFactory;

	/**
	 * Indicates if the CachePool instance was implicitly/automatically created
	 * by this instance, indicating that it should be automatically destroy.
	 */
	private boolean cachePoolImplicitlyCreated = false;

	/**
     * Default constructor
     */
	public RedisCacheManager() {

	}

	public RedisCachePool getCachePool() {
		return cachePool;
	}
	
	public String getShiroCacheKeyPrefix() {
		return shiroCacheKeyPrefix;
	}

	public void setShiroCacheKeyPrefix(String shiroCacheKeyPrefix) {
		this.shiroCacheKeyPrefix = shiroCacheKeyPrefix;
	}

	public RedisCacheConfigFactory getConfigFactory() {
		return configFactory;
	}

	public void setConfigFactory(RedisCacheConfigFactory configFactory) {
		this.configFactory = configFactory;
	}

	@Override
	public final void init() throws ShiroException {
		ensureCacheManager();
	}

	/**
	 * Initialize the cache pool. If current cache pool is null, 
	 * then create CachePool instance, else use the exist.
	 */
	private RedisCachePool ensureCacheManager() {
		try {
			if (null == this.cachePool) {
				if (logger.isDebugEnabled()) {
					logger.debug("cachePoolManager property not set. Constructing cachePoolManager instance... ");
				}
				this.cachePool = new RedisCachePool(this.configFactory);
				if (logger.isTraceEnabled()) {
					logger.trace("instantiated Redis cachePool instance.");
				}
				this.cachePoolImplicitlyCreated = true;
				if (logger.isDebugEnabled()) {
					logger.debug("implicit cachePool created successfully.");
				}
			}
			return this.cachePool;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if (logger.isDebugEnabled()) {
            logger.debug("Acquiring Cache instance named [" + name + "]");
        }
		RedisCache redisCache = ensureCacheManager().getCache(name);
		redisCache.setShiroCacheKeyPrefix(getShiroCacheKeyPrefix());	
		return redisCache;
	}

	@Override
	public void destroy() throws Exception {
		if (this.cachePoolImplicitlyCreated) {
			try {
				RedisCachePool cachePool = getCachePool();
				cachePool.destroy();
			} catch (Exception e) {
				if (logger.isWarnEnabled()) {
                    logger.warn("Unable to cleanly shutdown implicitly created CachePool instance.  " +
                            "Ignoring (shutting down)...");
                }
			}
			this.cachePoolImplicitlyCreated = false;
		}
	}

}
