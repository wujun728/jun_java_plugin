package com.feng.cache.guava;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class GuavaCache implements Cache{
	
	public GuavaCache (String name,com.google.common.cache.Cache<Object, Object> cache,boolean allowNullValues){
		this.name = name;
		this.cache = cache;
		this.allowNullValues = allowNullValues;
	}
	/**
	 * 缓存的具体实现
	 */
	private final com.google.common.cache.Cache<Object, Object> cache;

	/**
	 * 缓存名称
	 */
	private final String name;
	
	/**
	 * 是否允许为空
	 */
	private final boolean allowNullValues;
	
	

	public boolean isAllowNullValues() {
		return allowNullValues;
	}

	@Override
	public Object getNativeCache() {
		return this.cache;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object Value = cache.getIfPresent(key);
		return (Value != null ? new SimpleValueWrapper(Value) : null);
	}

	@Override
	public void put(Object key, Object value) {
		cache.put(key, value);
	}

	@Override
	public void evict(Object key) {
		cache.invalidate(key);
	}

	@Override
	public void clear() {
		cache.invalidateAll();
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
