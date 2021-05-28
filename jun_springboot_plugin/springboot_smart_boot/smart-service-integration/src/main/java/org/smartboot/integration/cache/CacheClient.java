package org.smartboot.integration.cache;

import java.util.List;

/**
 * 缓存服务客户端接口
 * 
 * @author Wujun
 * @version v0.1 2015年11月06日 下午1:19 Seer Exp.
 */
public interface CacheClient {
	/**
	 * 存储缓存自定义对象
	 * 
	 * @param key
	 * @param object
	 */
	public void putObject(String key, Object object, long expire);

	public void set(final String key, final String str, final long exprie);

	public boolean setNX(final String key, final String str, final long exprie);

	/**
	 * 获取缓存中存储的自定义对象
	 * 
	 * @param key
	 */
	public <T> T getObject(String key);

	/**
	 * 获取缓存中的字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key);

	/**
	 * 移除缓存中存储的对象
	 * 
	 * @param key
	 */
	public Long remove(String key);

	/**
	 * @param key
	 * @return
	 */
	public Long incr(String key);

	public Long decr(String key);

	/**
	 * 键名匹配
	 * 
	 * @param pattern
	 * @return
	 */
	public List<String> keys(String pattern);

	/**
	 * 是否存在该Key
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key);
}
