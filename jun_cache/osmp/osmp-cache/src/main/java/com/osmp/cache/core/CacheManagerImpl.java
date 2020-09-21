/*   
 * Project: OSMP
 * FileName: CacheManagerImpl.java
 * version: V1.0
 */
package com.osmp.cache.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 下午1:40:39
 */

public class CacheManagerImpl implements CacheManager {

	private Map<Method, CacheableDefine> cacheableDefineMap = new ConcurrentHashMap<Method, CacheableDefine>();

	@Override
	public CacheableDefine getCacheableDefine(Method method) {
		return cacheableDefineMap.get(method);
	}

	@Override
	public void putCacheableDefine(CacheableDefine cacheableDefine) {
		if (cacheableDefine == null || cacheableDefine.getMethod() == null) {
			throw new IllegalArgumentException("参数不合法");
		}

		cacheableDefineMap.put(cacheableDefine.getMethod(), cacheableDefine);
	}

	@Override
	public List<CacheableDefine> getCacheableDefines() {
		return new ArrayList<CacheableDefine>(cacheableDefineMap.values());
	}

	@Override
	public CacheableDefine getCacheableDefineByID(String id) {
		CacheableDefine result = null;
		for (Entry<Method, CacheableDefine> data : cacheableDefineMap.entrySet()) {
			if (data.getValue().getId().equals(id)) {
				result = data.getValue();
				break;
			}
		}
		return result;
	}

	@Override
	public boolean isOpen() {
		return true;
	}

}
