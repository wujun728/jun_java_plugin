/**
 * 
 */
package com.opensource.nredis.proxy.monitor.service;

import com.opensource.nredis.proxy.monitor.model.RedisConfigInfo;

/**
 * @author liubing
 *
 */
public interface IRedisService {
	
	/**
	 * 动态获取redis 使用情况
	 * @param host
	 * @return
	 */
	public RedisConfigInfo getRedisConfigInfo(String host,int port);
}
