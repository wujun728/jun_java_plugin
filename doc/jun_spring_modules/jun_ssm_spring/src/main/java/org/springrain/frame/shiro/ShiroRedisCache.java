package org.springrain.frame.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springrain.frame.cache.ICache;
import org.springrain.frame.common.BaseLogger;
import org.springrain.frame.util.SerializeUtils;

/**
 * Shiro 实现的缓存
 * 
 * @author caomei
 *
 * @param <K>
 * @param <V>
 */
public class ShiroRedisCache<K, V> extends BaseLogger implements Cache<K, V> {
	private String name;
	private ICache cache;

	public ShiroRedisCache(String name, ICache cached) {
		this.name = name;
		this.cache = cached;
	}

	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(K key) {
		if (key instanceof String) {
			String preKey = key.toString();
			return preKey.getBytes();
		} else {
			return SerializeUtils.serialize(key);
		}
	}

	private byte[] getByteName() {
		return name.getBytes();

	}

	
	@Override
	public V get(K key) throws CacheException {
		if (logger.isDebugEnabled()){
			logger.debug("根据key从Redis中获取对象 key [{}]",key);
		}
		try {
			if (key == null) {
				return null;
			} else {
				V value = (V) cache.hGet(getByteName(), getByteKey(key));
				return value;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}

	}

	@Override
	public V put(K key, V value) throws CacheException {
		if (logger.isDebugEnabled()){
			logger.debug("根据key从存储 key [{}]",key);
		}
		try {
			cache.hSet(getByteName(), getByteKey(key), SerializeUtils.serialize(value), null);
			return value;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		if (logger.isDebugEnabled()){
			logger.debug("从redis中删除 key [{}]",key);
		}
		try {
			V previous = get(key);
			cache.hDel(getByteName(), getByteKey(key));
			return previous;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	@Override
	public void clear() throws CacheException {
		if (logger.isDebugEnabled()){
			logger.debug("从redis中删除所有元素");
		}
		try {
			cache.del(getByteName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	@Override
	public int size() {
		try {
			Long longSize = new Long(cache.hLen(getByteName()));
			return longSize.intValue();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	
	@Override
	public Set<K> keys() {
		try {
			Set<K> keys = cache.hKeys(getByteName());
			return keys;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	
	@Override
	public Collection<V> values() {
		try {
			Collection<V> values = cache.hVals(getByteName());
			return values;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ICache getCached() {
		return cache;
	}

	public void setCached(ICache cached) {
		this.cache = cached;
	}

}
