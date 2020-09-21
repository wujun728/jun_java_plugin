/*   
 * Project: OSMP
 * FileName: ZookeeperService.java
 * version: V1.0
 */
package com.osmp.intf.define.service;

import java.util.List;

/**
 * Description:Zookeeper工具类
 * 
 * @author: wangkaiping
 * @date: 2015年6月9日 上午11:20:29
 */
public interface ZookeeperService {
	
	public static final String SERVICE = "/service";
	public static final String STATUS = "/status";
	public static final String ROOT_PATH = "/osmp";

	/**
	 * 创建永久节点
	 * 
	 * @param path
	 * @param data
	 */
	public void createNode(String path) throws Exception;

	/**
	 * 创建永久节点带数据
	 * 
	 * @param path
	 * @param data
	 * @throws Exception
	 */
	public void createNode(String path, String data) throws Exception;


	/**
	 * 创建临时节点
	 * 
	 * @param path
	 * @param data
	 */
	public void createEpheMeralNode(String path) throws Exception;

	/**
	 * 修改节点数据
	 * 
	 * @param path
	 * @param data
	 */
	public void setNodeData(String path, String data) throws Exception;

	/**
	 * 删除节点
	 * 
	 * @param path
	 */
	public void deleteNode(String path) throws Exception;
	
	/**
	 * 是否连接
	 * 
	 * @return
	 */
	public boolean isConnected() throws Exception;
	
	/**
	 * 节点是否存在
	 * 
	 * @param path
	 * @return
	 */
	public boolean exists(String path) throws Exception;

	/**
	 * 得到节点下所有子节点信息
	 * 
	 * @param path
	 * @return
	 */
	public List<String> getChilds(String path) throws Exception;

	/**
	 * 根据bundle删除服务节点
	 * 
	 * @param bundle
	 * @throws Exception
	 */
	public void deleteNodeByBundle(String bundle) throws Exception;

	/**
	 * 获取节点的数据
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String getNodeDate(String path) throws Exception;

}
