package net.oschina.j2cache;

import java.util.Properties;

/**
 * 获取缓存容器实现容器等的实例<br>
 * 支持各种第三方的实现，如：[ehcache|redis|memcached]等，参照redis实现
 */
public interface CacheProvider {
	
	/**
	 * 缓存的标识名称
	 * @return return cache provider name
	 */
	String name();
	
	/**
	 * 构建实例
	 * @param regionName {String} region
	 * @param autoCreate {boolean} 自动创建
	 * @param listener {CacheExpiredListener} 监听器
	 * @return {Cache }
	 * @throws CacheException 缓存异常
	 */
	Cache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener) throws CacheException;
	
	/**
	 * 在构建一个容器实例是调用的方法，初始化一些配置信息等，并启动
	 * @param props {@link Properties}
	 * @throws CacheException 缓存异常
	 */
	void start(Properties props) throws CacheException;

    /**
     * 启动容器
     * @param confFilePath 配置文件路径
     * @throws CacheException 缓存异常
     */
    void start(String confFilePath) throws CacheException;
	
	/**
	 * 关闭容器实例
	 */
	void stop();

}
