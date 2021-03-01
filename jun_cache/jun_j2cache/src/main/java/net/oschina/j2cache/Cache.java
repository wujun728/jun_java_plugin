package net.oschina.j2cache;

import java.util.List;

/**
 * 缓存工具[ehcache|redis等]的几个底层操作方法：get/put/update/keys/evict/clear/destroy等<br>
 * 支持各种第三方的实现，如：[ehcache|redis|memcached]等
 */
public interface Cache {

	/**
	 * 从缓存中取出一个数据对象
	 * @param key {String} -- cache key
	 * @return 返回Object 或者 NULL
	 * @throws CacheException 缓存异常
	 */
	Object get(Object key) throws CacheException;
	
	/**
	 * 添加一个数据对象到缓存
	 * @param key key {String} -- cache key
	 * @param value value
	 * @throws CacheException 缓存异常
	 */
	void put(Object key, Object value) throws CacheException;

	/**
	 * 从缓存中销毁/删除Key对应的数据
	 * @param key key {String} -- cache key
	 * @throws CacheException 缓存异常
	 */
	void evict(Object key) throws CacheException;
	
	/**
	 * 从缓存中批量销毁/删除Keys对应的数据
	 * @param keys {List} -- cache keys
	 * @throws CacheException 缓存异常
	 */
	@SuppressWarnings("rawtypes")
	void evict(List keys) throws CacheException;

	/**
	 * 取得当前region下所有的key
	 * @return keys {List}
	 * @throws CacheException 缓存异常
	 */
	List keys() throws CacheException;
	
	/**
	 * 清除所有的缓存数据
	 * @throws CacheException 缓存异常
	 */
	void clear() throws CacheException;

	/**
	 * 销毁连接实例 -- 实际做的工作是清理掉所有数据
	 * @throws CacheException 缓存异常
	 */
	void destroy() throws CacheException;

}
