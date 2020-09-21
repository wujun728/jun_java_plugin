/*   
 * Project: 基础组件 
 * FileName: CacheManagerServiceImpl.java
 * version: V1.0
 */
package com.osmp.cache.osgi.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import com.osmp.cache.define.core.CacheManagerService;
import com.osmp.cache.define.core.CacheableDefine;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月19日 下午2:13:26
 */

public class CacheManagerServiceImpl implements CacheManagerService {

	private Map<String, CacheableDefine> cacheableDefineMap = new ConcurrentHashMap<String, CacheableDefine>();
	private static final Lock lock = new ReentrantLock();
	private static final int maxCache = 10000;
	private static final CacheManager manager = CacheManager.create();
	// 全局缓存开关
	private static boolean flag = true;
	private static Cache cache;

	@Override
	public CacheableDefine getCacheableDefine(String id) {
		return cacheableDefineMap.get(id);
	}

	@Override
	public void putCacheableDefine(CacheableDefine cacheableDefine) {
		if (cacheableDefine == null || cacheableDefine.getMethod() == null) {
			throw new IllegalArgumentException("参数不合法");
		}

		cacheableDefineMap.put(cacheableDefine.getId(), cacheableDefine);

	}

	@Override
	public List<CacheableDefine> getCacheableDefines() {
		return new ArrayList<CacheableDefine>(cacheableDefineMap.values());
	}

	@Override
	public CacheableDefine getCacheableDefineByID(String id) {
		return cacheableDefineMap.get(id);
	}

	@Override
	public Cache getDefaultCache() {
		lock.lock();
		try {
			cache = manager.getCache("defaultCache");
			if (null == cache) {// 校验cache是否已经存在 add by wangkaiping
				Cache temp = new Cache("defaultCache", maxCache, false, false, 1800l, 1800l);
				manager.addCache(temp);
				cache = manager.getCache("defaultCache");
			}
		} finally {
			lock.unlock();
		}
		return cache;
	}

	@Override
	public void openCache() {
		flag = true;
	}

	@Override
	public void closeCache() {
		flag = false;
	}

	@Override
	public boolean isOpen() {
		return flag;
	}

}
