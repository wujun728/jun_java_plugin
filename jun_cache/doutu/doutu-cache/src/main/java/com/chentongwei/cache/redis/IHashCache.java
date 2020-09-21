package com.chentongwei.cache.redis;

/**
 * Redis的Hash操作接口
 * 
 * @author TongWei.Chen 2017-5-21 14:11:55
 */
public interface IHashCache {
	
	/**
	 * 缓存数据到hash
	 * 
	 * @param key key
	 * @param hashKey map的key
	 * @param value map的value
	 */
	void cacheHash(final String key, final Object hashKey, final Object value);
	
	/**
	 * 根据key获取hash的value
	 * 
	 * @param key：key
	 * @param hashKey：map的key
	 * @return
	 */
	Object getHash(final String key, final Object hashKey);
}
