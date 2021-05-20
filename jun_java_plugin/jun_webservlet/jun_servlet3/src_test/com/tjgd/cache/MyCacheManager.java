package com.tjgd.cache;

import com.tjgd.bean.Role;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class MyCacheManager {
	private CacheManager manager; // 缓存管理器
	private Cache cache; // 缓存
	// -------初始化缓存--------------------------------------

	 public void initialize() {
		manager = CacheManager.create();
		cache = manager.getCache("ehcache");// 获取一个Cache对象
	}

	// -------向缓存中存入内容--------------------------------
	public void put(int key, Role value) { // 将对象加入缓存
		Element element = new Element(key, value);
		cache.put(element);
	}

	// -------根据Role.getId()取得特定的Role对象---------------
	public Role get(int key) {
		Element value = cache.get(key); // 根据key取出值
		if (value!=null) {
			Role role = (Role) value.getObjectValue();// 从缓存中取出 一个Role对象
			return role;
		}
		return null;
		

	} 
}
