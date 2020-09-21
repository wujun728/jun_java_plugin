/**
 * 
 */
package com.opensource.nredis.proxy.monitor.zookeeper.factory;

import org.I0Itec.zkclient.ZkClient;

/**
 * 获取客户端类
 * @author liubing
 *
 */
public class RegistryFactory implements IRegistryFactory {
	
	private String addressURL;
	
	private int sessionTimeout;
	
	private int timeout;
	
	/* (non-Javadoc)
	 * @see com.opensource.nredis.proxy.monitor.zookeeper.factory.IRegistryFactory#getZkClient(java.lang.String, int, int)
	 */
	@Override
	public ZkClient getZkClient(String addressURL, int sessionTimeout,
			int timeout) {
		ZkClient zkClient = new ZkClient(addressURL, sessionTimeout, timeout);
		return zkClient;
	}
	/**
	 * @return the addressURL
	 */
	public String getAddressURL() {
		return addressURL;
	}
	/**
	 * @param addressURL the addressURL to set
	 */
	public void setAddressURL(String addressURL) {
		this.addressURL = addressURL;
	}
	/**
	 * @return the sessionTimeout
	 */
	public int getSessionTimeout() {
		return sessionTimeout;
	}
	/**
	 * @param sessionTimeout the sessionTimeout to set
	 */
	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	
}
