package com.chentongwei.cache.redis;

import java.util.Map;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Redis的Hash操作接口
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

	/**
	 * 删除hash中的value
	 *
	 * @param key：key
	 * @param hashKeys：map的keys
	 * @return
	 */
	long deleteHash(final String key, final Object ... hashKeys);

	/**
	 * 根据key查询全部数据
	 *
	 * @param key：key
	 * @return
	 */
	Map<Object, Object> entries(final String key);

}
