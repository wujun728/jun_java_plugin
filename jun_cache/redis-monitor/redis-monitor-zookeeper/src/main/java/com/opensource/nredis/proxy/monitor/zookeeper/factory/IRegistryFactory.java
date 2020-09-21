/**
 * 
 */
package com.opensource.nredis.proxy.monitor.zookeeper.factory;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author liubing
 *
 */
public interface IRegistryFactory {
	
	/**
	 * 获取zk 客户端
	 * @param addressURL 地址
	 * @param sessionTimeout session 超时时间
	 * @param timeout 超时时间
	 * @return
	 */
	public ZkClient getZkClient(String addressURL,int sessionTimeout,int timeout);
}
