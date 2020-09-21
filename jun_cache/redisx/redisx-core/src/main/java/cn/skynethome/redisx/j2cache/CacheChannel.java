package cn.skynethome.redisx.j2cache;

import java.util.List;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[CacheChannel]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:39:38]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:39:38]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public interface CacheChannel {

	public final static byte LEVEL_1 = 1;
	public final static byte LEVEL_2 = 2;
	
	/**
	 * 获取缓存中的数据
	 * @param region: Cache Region name
	 * @param key: Cache key
	 * @return cache object
	 */
	public CacheObject get(String region, Object key);
	
	/**
	 * 写入缓存
	 * @param region: Cache Region name
	 * @param key: Cache key
	 * @param value: Cache value
	 */
	public void set(String region, Object key, Object value);

	/**
	 * 删除缓存
	 * @param region:  Cache Region name
	 * @param key: Cache key
	 */
	public void evict(String region, Object key) ;

	/**
	 * 批量删除缓存
	 * @param region: Cache region name
	 * @param keys: Cache key
	 */
	@SuppressWarnings({ "rawtypes" })
	public void batchEvict(String region, List keys) ;

	/**
	 * Clear the cache
	 * @param region: Cache region name
	 */
	public void clear(String region) throws CacheException ;
	
	/**
	 * Get cache region keys
	 * @param region: Cache region name
	 * @return key list
	 */
	@SuppressWarnings("rawtypes")
	public List keys(String region) throws CacheException ;

	/**
	 * 关闭到通道的连接
	 */
	public void close() ;
}
