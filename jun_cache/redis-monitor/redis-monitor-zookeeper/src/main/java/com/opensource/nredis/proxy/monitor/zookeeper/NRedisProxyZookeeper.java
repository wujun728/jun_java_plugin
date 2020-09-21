/**
 * 
 */
package com.opensource.nredis.proxy.monitor.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.opensource.netty.redis.proxy.core.log.impl.LoggerUtils;
import com.opensource.nredis.proxy.monitor.zookeeper.enums.ZkNodeType;
import com.opensource.nredis.proxy.monitor.zookeeper.factory.IRegistryFactory;
import com.opensource.nredis.proxy.monitor.zookeeper.factory.RegistryFactory;
import com.opensource.nredis.proxy.monitor.zookeeper.utils.ZkUtils;

/**
 * @author liubing
 *
 */
public class NRedisProxyZookeeper {

	private String addressURL;

	private int sessionTimeout;

	private int timeout;

	private ZkClient zkClient;

	/**
	 * 初始化
	 */
	public void init() {
		IRegistryFactory registryFactory = new RegistryFactory();
		zkClient = registryFactory.getZkClient(addressURL, sessionTimeout,
				sessionTimeout);
	}

	/**
	 * 修改数据
	 * 
	 * @param path
	 * @param data
	 * @return
	 */
	public void setNodeData(String parentPath,String path, String data) {
		try {
			path=ZkUtils.toNodePath(parentPath, path, ZkNodeType.AVAILABLE_SERVER);
			 zkClient.writeData(path, data);
		} catch (Exception e) {
			LoggerUtils.error("getChildren error,parentPath:" + parentPath
					+ ",path:" + path, e);
		}

	}
	
	/**
     * 获取节点值
     * 
     * @param path
     * @return
     */
    public String getNodeData(String parentPath,String path) {

            String data = null;
            try {
            	path=ZkUtils.toNodePath(parentPath, path, ZkNodeType.AVAILABLE_SERVER);
            	data = zkClient.readData(path, true);
                return data;
            } catch (Exception e) {
            	LoggerUtils.error("getNodeData error,parentPath:" + parentPath
    					+ ",path:" + path, e);
                return null;
            }
    }
    
	/**
	 * 获取path子节点名列表
	 * 
	 * @param path
	 * @return
	 */
	public List<String> getChildren(String parentPath, String path) {

		try {
			path = ZkUtils.toNodePath(parentPath, path,
					ZkNodeType.AVAILABLE_SERVER);
			return zkClient.getChildren(path);
		} catch (Exception e) {
			LoggerUtils.error("getChildren error,parentPath:" + parentPath
					+ ",path:" + path, e);
			return null;
		}
	}
	
	/**
	 * 获取path子节点名列表
	 * 
	 * @param path
	 * @return
	 */
	public List<String> getAllChildren() {

		try {
			String path = ZkUtils.toNodeTypePath(ZkNodeType.AVAILABLE_SERVER);
			return zkClient.getChildren(path);
		} catch (Exception e) {
			LoggerUtils.error("getAllChildren error ,path:"+ZkUtils.toNodeTypePath(ZkNodeType.AVAILABLE_SERVER), e);
			return null;
		}
	}
	
	/**
	 * 删除节点
	 * 
	 * @param path
	 * @return
	 */
	public boolean deleteNode(String parentPath, String path) {
		try {
			path = ZkUtils.toNodePath(parentPath, path,
					ZkNodeType.AVAILABLE_SERVER);
			return zkClient.delete(path);
		} catch (Exception e) {
			LoggerUtils.error("deleteNode error,parentPath:" + parentPath
					+ ",path:" + path, e);
			return false;
		}
	}

	/**
	 * 是否存在path路径节点
	 * 
	 * @param path
	 * @return
	 */
	public boolean exists(String parentPath, String path) {
		try {
			path = ZkUtils.toNodePath(parentPath, path,
					ZkNodeType.AVAILABLE_SERVER);
			return zkClient.exists(path);
		} catch (Exception e) {
			LoggerUtils.error("exists error,parentPath:" + parentPath
					+ ",path:" + path, e);
		}
		return false;
	}

	/**
	 * 销废
	 */
	public void destroy() {
		zkClient.close();
	}

	/**
	 * @return the addressURL
	 */
	public String getAddressURL() {
		return addressURL;
	}

	/**
	 * @param addressURL
	 *            the addressURL to set
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
	 * @param sessionTimeout
	 *            the sessionTimeout to set
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
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the zkClient
	 */
	public ZkClient getZkClient() {
		return zkClient;
	}


	
	
}
