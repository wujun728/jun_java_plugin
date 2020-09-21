/*   
 * Project: OSMP
 * FileName: CacheManagerService.java
 * version: V1.0
 */
package com.osmp.cache.define.core;

import java.util.List;

import net.sf.ehcache.Cache;

/**
 * Description:缓存管理服务接口
 * 
 * @author: wangkaiping
 * @date: 2014年9月19日 下午1:44:31
 */

public interface CacheManagerService {

	/**
	 * 根据method获取CacheableDefine
	 * 
	 * @param method
	 * @return
	 */
	public CacheableDefine getCacheableDefine(String id);

	/**
	 * 添加
	 * 
	 * @param cacheableDefine
	 */
	public void putCacheableDefine(CacheableDefine cacheableDefine);

	/**
	 * 获取定义集合
	 * 
	 * @return
	 */
	public List<CacheableDefine> getCacheableDefines();

	/**
	 * 通过ID获取特定的缓存配置
	 * 
	 * @return
	 */
	public CacheableDefine getCacheableDefineByID(String id);

	/**
	 * 缓存是否打开
	 * 
	 * @return
	 */
	public boolean isOpen();

	/**
	 * 打开全局缓存
	 */
	public void openCache();

	/**
	 * 关闭全局缓存
	 */
	public void closeCache();

	/**
	 * 得到缓存
	 * 
	 * @Description:
	 * @param @return
	 * @return Cache
	 * @throws
	 */
	public Cache getDefaultCache();

}
