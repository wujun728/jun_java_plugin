package org.coody.framework.cache.instance.iface;

/**
 * 切面缓存超类
 * @author Coody
 *
 */
public interface IcopCacheFace {

	/**
	 * 设置缓存
	 * @param key 缓存KEY
	 * @param value 缓存内容
	 * @param time 缓存时间
	 */
	public void setCache(String key, Object value, Integer time);

	/**
	 * 设置缓存
	 * @param key 缓存KEY
	 * @param value 缓存内容
	 */
	public void setCache(String key, Object value);
	
	/**
	 * 获取缓存
	 * @param key 缓存KEY
	 * @return 缓存内容
	 */
	public <T> T getCache(String key);
	
	/**
	 * 判断缓存是否存在
	 * @param key 缓存KEY
	 * @return 缓存内容
	 */
	public Boolean contains(String key);
	
	/**
	 * 删除缓存
	 * @param key 缓存KEY
	 */
	public void delCache(String key);
}
