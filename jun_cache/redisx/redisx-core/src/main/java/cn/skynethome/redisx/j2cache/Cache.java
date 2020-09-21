package cn.skynethome.redisx.j2cache;

import java.util.List;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[Cache]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:39:30]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:39:30]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public interface Cache {

	/**
	 * Get an item from the cache, nontransactionally
	 * @param key cache key
	 * @return the cached object or null
	 */
	public Object get(Object key) throws CacheException;
	
	/**
	 * Add an item to the cache, nontransactionally, with
	 * failfast semantics
	 * @param key cache key
	 * @param value cache value
	 */
	public void put(Object key, Object value) throws CacheException;
	
	/**
	 * Add an item to the cache
	 * @param key cache key
	 * @param value cache value
	 */
	public void update(Object key, Object value) throws CacheException;

	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException ;
	
	/**
	 * @param key Cache key
	 * Remove an item from the cache
	 */
	public void evict(Object key) throws CacheException;
	
	/**
	 * Batch remove cache objects
	 * @param keys the cache keys to be evicted
	 */
	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException;
	
	/**
	 * Clear the cache
	 */
	public void clear() throws CacheException;
	
	/**
	 * Clean up
	 */
	public void destroy() throws CacheException;
	
	
	public boolean exists(Object key);
	
}
