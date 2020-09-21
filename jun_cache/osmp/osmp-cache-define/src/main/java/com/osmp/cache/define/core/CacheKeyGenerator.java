/*   
 * Project: OSMP
 * FileName: CacheKeyGenerator.java
 * version: V1.0
 */
package com.osmp.cache.define.core;

/**
 * Description: key生成器
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 下午1:56:46
 */

public interface CacheKeyGenerator {
	/**
	 * 获取缓存key
	 * 
	 * @param cacheDefine
	 * @param args
	 * @return
	 */
	public String getKey(CacheableDefine cacheDefine, Object[] args);

}
