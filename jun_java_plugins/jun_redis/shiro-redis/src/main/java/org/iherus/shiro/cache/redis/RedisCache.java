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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.iherus.shiro.util.ArrayUtils;
import org.iherus.shiro.util.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

/**
 * <p>缓存接口实现类</p>
 * <p>Description:实现Shiro缓存接口，提供远程获取缓存对象、远程保存缓存对象和远程清空缓存库等功能</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月11日-下午4:06:06
 */
public class RedisCache<K, V> implements Cache<K, V>, Serializable {

	/**
	 * Required for serialization support.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 4521785299624111291L;

	/**
     * Private internal log instance.
     */
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

	/**
	 * Default Shiro cache name.
	 */
	private static final String DEFAULT_CACHE_NAME = "shiro_redis_cache";

	/**
	 * Shiro cache name.
	 */
	private String name = DEFAULT_CACHE_NAME;

	/**
	 * Shiro cache key prefix.
	 */
	private String shiroCacheKeyPrefix;
	
	/**
	 * The wrapped RedisCachePool instance.
	 */
	protected RedisCachePool cachePool;

	/**
	 * Constructs a new RedisCache instance with the given CachePool.
	 */
	public RedisCache(RedisCachePool cachePool) {
		this.cachePool = cachePool;
	}

	/**
	 * Constructs a new RedisCache instance with the given CachePool and name.
	 */
	public RedisCache(RedisCachePool cachePool, final String name) {
		if (cachePool == null) {
			throw new IllegalArgumentException("CachePool argument cannot be null.");
		}
		this.cachePool = cachePool;
		this.name = name;
	}

	/**
	 * Gets bytes key with key prefix.
	 */
	private final byte[] getKeyToBytes(K key) {
		if (key instanceof byte[]) {
			return (byte[]) key;
		}
		final byte[] preBytes = getPrefixToBytes();
		byte[] keyBytes = SerializeUtils.serialize(key);
		return ArrayUtils.mergeAll(preBytes, keyBytes);
	}

	/**
	 * Gets key prefix to byte[].
	 */
	private final byte[] getPrefixToBytes() {
		return SafeEncoder.encode(getShiroCacheKeyPrefix());
	}

	/**
	 * Gets pattern to byte[] base on prefix.
	 */
	private final byte[] getKeyPattern() {
		return SafeEncoder.encode(getShiroCacheKeyPrefix() + "*");
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting object from cache [" + getName() + "] for key [" + key + "]");
		}
		Jedis jedis = initJedis(cachePool);
		try {
			if (key == null) {
				return null;
			} else {
				byte[] value = jedis.get(getKeyToBytes(key));
				if (value == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("Cache for [" + key + "] is null.");
					}
					return null;
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Cache for [" + key + "] is exist, ready to use it.");
					}
					return (V) SerializeUtils.deserialize(value);
				}
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		} finally {
			close(jedis);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if (logger.isDebugEnabled()) {
			logger.debug("Putting object in cache [" + getName() + "] for key [" + key + "]");
		}
		Jedis jedis = initJedis(cachePool);
		try {
			V previous = get(key);
			byte[] nval = SerializeUtils.serialize(value);
			jedis.set(getKeyToBytes(key), nval);
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		} finally {
			close(jedis);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		if (logger.isDebugEnabled()) {
			logger.debug("Removing object from cache [" + getName() + "] for key [" + key + "]");
		}
		Jedis jedis = initJedis(cachePool);
		try {
			V previous = get(key);
			long statusCode = jedis.del(getKeyToBytes(key));
			if (statusCode > 0) {
				if (logger.isInfoEnabled()) {
					logger.info("Remove key [{}] successfully.", key);
				}
			}
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		} finally {
			close(jedis);
		}
	}

	@Override
	public void clear() throws CacheException {
		if (logger.isDebugEnabled()) {
			logger.debug("Clearing all objects from cache [" + getName() + "]");
		}
		Jedis jedis = initJedis(cachePool);
		try {
			String statusCode = jedis.flushDB();
			/**
			 * Temporarily clear the current DB. If the intensity is too large,
			 * then could remove value base on the keys.
			 */
			//jedis.del(keys);
			if ("OK".equalsIgnoreCase(statusCode)) {
				if (logger.isInfoEnabled()) {
					logger.info("Flush DB_{} successfully.", jedis.getDB());
				}
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		} finally {
			close(jedis);
		}
	}

	@Override
	public int size() {
		Jedis jedis = initJedis(cachePool);
		try {
			long size = jedis.dbSize();
			return Long.valueOf(size).intValue();
		} catch (Throwable t) {
			throw new CacheException(t);
		} finally {
			close(jedis);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		Jedis jedis = initJedis(cachePool);
		try {
			Set<byte[]> keySet = jedis.keys(getKeyPattern());
			if (!CollectionUtils.isEmpty(keySet)) {
				Set<K> keys = new LinkedHashSet<K>();
				for (byte[] key : keySet) {
					keys.add((K) key);
				}
				return keys;
			} else {
				return Collections.emptySet();
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		} finally {
			close(jedis);
		}
	}

	@Override
	public Collection<V> values() {
		try {
			Set<K> keys = keys();
			if (!CollectionUtils.isEmpty(keys)) {
				List<V> values = new ArrayList<V>(keys.size());
				for (K key : keys) {
					V value = get(key);
					if (value != null) {
						values.add(value);
					}
				}
				return Collections.unmodifiableList(values);
			} else {
				return Collections.emptyList();
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}
	
	/**
	 * Constructs a Jedis instance.
	 */
	public static Jedis initJedis(final RedisCachePool cachePool) {
		if (cachePool == null) {
			throw new IllegalArgumentException("CachePool argument cannot be null.");
		}
		Jedis jedis = null;
		try {
			jedis = cachePool.getResource();
			jedis.select(cachePool.getDatabaseIndex());
			if (logger.isDebugEnabled()) {
				logger.debug("The current application database is located in DB_{}.", jedis.getDB());
			}
			return jedis;
		} catch (Exception e) {
			close(jedis); //If throw exception, and Jedis is not null, then close Jedis instance.
			throw new CacheException(e);
		} 
	}
	
	/**
	 * Close Jedis instance.
	 */
	public static void close(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	@Override
	public String toString() {
		return "RedisCache [" + getName() + "]";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShiroCacheKeyPrefix() {
		return shiroCacheKeyPrefix;
	}

	public void setShiroCacheKeyPrefix(String shiroCacheKeyPrefix) {
		this.shiroCacheKeyPrefix = shiroCacheKeyPrefix;
	}

}
