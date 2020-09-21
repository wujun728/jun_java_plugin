/*   
 * Project: OSMP
 * FileName: Cacheable.java
 * version: V1.0
 */
package com.osmp.cache.osgi.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.osmp.cache.define.core.CacheManagerService;
import com.osmp.cache.define.core.CacheableDefine;
import com.osmp.cache.osgi.api.CacheApiService;
import com.osmp.cache.osgi.api.entity.CacheDefine;

/**
 * Description:全局缓存API接口
 * 
 * @author: wangkaiping
 * @date: 2014年12月2日 下午5:26:39
 */
public class CacheApiServiceImpl implements CacheApiService {

	// 打开全局缓存
	private static final String CACHE_OPEN = "cache_open";
	// 关闭全局缓存
	private static final String CACHE_CLOSE = "cache_close";
	// 取得所有缓存的定义
	private static final String CACHE_GETDEFINE = "cache_getdefine";
	// 更新缓存定义
	private static final String CACHE_UPDATE = "cache_update";
	// 删除缓存
	private static final String CACHE_REMOVE = "cache_remove";
	// 所有已缓存的数据
	private static final String CACHE_ITEM = "cache_item";
	// 缓存基本信息 包括占用内存、缓存的实体对象数
	private static final String CACHE_INFO = "cache_info";

	private CacheManagerService cacheService;

	public CacheManagerService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheManagerService cacheService) {
		this.cacheService = cacheService;
	}

	@Override
	public void update(String target) {
		if (target.equals(CACHE_OPEN)) {
			cacheService.openCache();
		} else if (target.equals(CACHE_CLOSE)) {
			cacheService.closeCache();
		}
	}

	@Override
	public Object getData(String target, Map<String, Object> args) {
		Object obj = null;
		if (target.equals(CACHE_GETDEFINE)) {
			obj = this.changeCacheDefine();
		} else if (target.equals(CACHE_UPDATE)) {
			this.updateDefine(args);
		} else if (target.equals(CACHE_ITEM)) {// 返回所有缓存数据
			obj = getCacheElement(args);
		} else if (target.equals(CACHE_INFO)) {// 返回缓存基本信息
			obj = getCacheInfo();
		} else if (target.equals(CACHE_REMOVE)) {
			this.remove(args);
		}
		return obj;
	}

	/**
	 * 更新缓存
	 * 
	 * @param args
	 */
	public void updateDefine(Map<String, Object> map) {
		String[] id = (String[]) map.get("id");
		String[] state = (String[]) map.get("state");
		String[] timeToIdle = (String[]) map.get("timeToIdle");
		String[] timeToLive = (String[]) map.get("timeToLive");
		CacheableDefine de = this.cacheService.getCacheableDefineByID(id[0]);
		de.setState(Integer.valueOf(state[0]));
		de.setTimeToIdle(Integer.valueOf(timeToIdle[0]));
		de.setTimeToLive(Integer.valueOf(timeToLive[0]));
	}

	/**
	 * 缓存定义转换一下。原实体对象不能转换为JSON
	 * 
	 * @return
	 */
	public List<CacheDefine> changeCacheDefine() {
		List<CacheDefine> deList = new ArrayList<CacheDefine>();
		List<CacheableDefine> list = this.cacheService.getCacheableDefines();
		for (CacheableDefine i : list) {
			CacheDefine ca = new CacheDefine();
			ca.setId(i.getId());
			ca.setName(i.getName());
			ca.setPrefix(i.getPrefix());
			ca.setState(i.getState());
			ca.setTimeToIdle(i.getTimeToIdle());
			ca.setTimeToLive(i.getTimeToLive());
			deList.add(ca);
		}
		return deList;
	}

	/**
	 * 缓存基本信息
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<String, String> getCacheInfo() {
		Cache cache = this.cacheService.getDefaultCache();
		// 缓存对象数
		int size = cache.getSize();
		long memorySize = cache.getMemoryStoreSize();
		long hitCount = cache.getStatistics().cacheHitCount();
		long missCount = cache.getStatistics().cacheMissCount();
		Map<String, String> map = new HashMap<String, String>();
		map.put("size", String.valueOf(size));
		map.put("memorySize", String.valueOf(memorySize));
		map.put("hitCount", String.valueOf(hitCount));
		map.put("missCount", String.valueOf(missCount));
		return map;
	}

	/**
	 * 缓存数据列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> getCacheElement(Map<String, Object> args) {
		String itemKey = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (null != args && null != args.get("keys")) {
			String[] itemKeys = (String[]) args.get("keys");
			if (null != itemKeys && itemKeys.length == 1) {
				CacheableDefine cacheDefine = this.cacheService.getCacheableDefineByID(itemKeys[0]);
				itemKey = cacheDefine.getMethod().getDeclaringClass().getName() + "."
						+ cacheDefine.getMethod().getName();
			}
		}
		Cache cache = this.cacheService.getDefaultCache();
		List keys = cache.getKeys();
		Map<Object, Element> map = cache.getAll(keys);
		for (Map.Entry<Object, Element> i : map.entrySet()) {
			Object key = i.getKey();
			String temp = String.valueOf(key);
			if (null != itemKey && !(temp.indexOf(itemKey) != -1)) {
				continue;
			}
			Element val = i.getValue();
			Map<String, Object> result = new HashMap<String, Object>();
			if (null != val) {
				Object obj = val.getObjectValue();
				if (null != obj && obj.getClass().getClassLoader() == null) {
					result.put("itemVal", obj);
				} else {
					String item = ToStringBuilder.reflectionToString(obj);
					item = item.replaceAll("@.*?\\[", "[");
					result.put("itemVal", item);
				}
				result.put("itemKey", key);
				result.put("createTime", val.getCreationTime());
				result.put("lastAccessTime", val.getLastAccessTime());
				result.put("expirationTime", val.getExpirationTime());
				result.put("hitCount", val.getHitCount() - 1);
				list.add(result);
			}
		}
		return list;
	}

	/**
	 * 根据KEY删除缓存数据
	 * 
	 * @param map
	 */
	public void remove(Map<String, Object> map) {
		String[] cacheKey = (String[]) map.get("keys");
		if (null != cacheKey && cacheKey.length > 0) {
			Cache cache = this.cacheService.getDefaultCache();
			List<String> list = Arrays.asList(cacheKey);
			cache.removeAll(list);
		}
	}

	@Override
	public void update(String target, String value) {

	}
}
