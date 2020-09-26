package net.oschina.j2cache;

/**
 * 缓存失效/超时监听
 */
public interface CacheExpiredListener {
	
	/**
	 * 当缓存中的某个对象超时被清除的时候触发
	 * @param region: Cache region name
	 * @param key: cache key
	 */
	void notifyElementExpired(String region, Object key);

}
