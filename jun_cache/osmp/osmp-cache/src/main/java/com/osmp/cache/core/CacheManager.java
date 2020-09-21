/*   
 * Project: OSMP
 * FileName: CacheManager.java
 * version: V1.0
 */
package com.osmp.cache.core;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Description: 缓存管理器接口
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 下午1:38:47
 */

public interface CacheManager {
	public String CACHE_STATE = "sys_cache_state";

	/**
	 * 根据method获取CacheableDefine
	 * 
	 * @param method
	 * @return
	 */
	public CacheableDefine getCacheableDefine(Method method);

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

}
