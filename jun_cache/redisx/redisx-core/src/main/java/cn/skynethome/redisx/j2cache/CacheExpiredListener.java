package cn.skynethome.redisx.j2cache;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[CacheExpiredListener]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:39:54]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:39:54]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public interface CacheExpiredListener {

	/**
	 * 当缓存中的某个对象超时被清除的时候触发
	 * @param region: Cache region name
	 * @param key: cache key
	 */
	public void notifyElementExpired(String region, Object key) ;

}
