/*   
 * Project: OSMP
 * FileName: CacheConfigService.java
 * version: V1.0
 */
package com.osmp.cache.service;

import com.osmp.cache.core.CacheableDefine;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年8月15日 上午11:27:15
 */

public interface CacheConfigService {

	public void updateCachConfig(CacheableDefine args);

	public CacheableDefine getcacheDefineInfo(CacheableDefine args);

	public String getList();
}
