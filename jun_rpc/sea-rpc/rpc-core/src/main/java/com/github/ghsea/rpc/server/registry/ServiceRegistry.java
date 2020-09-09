package com.github.ghsea.rpc.server.registry;

import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ServiceProfile;

public interface ServiceRegistry {

	/**
	 * 将服务注册到注册中心
	 * 
	 * @param profile
	 */
	void register(final ServiceProfile profile) throws Exception;

	/**
	 * 取消注册
	 * 
	 * @param profile
	 * @throws Exception
	 */
	void unregister(ServiceProfile profile) throws Exception;

	/**
	 * 更新一个ServiceProfile
	 * 
	 * @param profile
	 * @throws Exception
	 */
	void update(ServiceProfile profile) throws Exception;

	/**
	 * 给一个分组增加一个机器节点
	 * 
	 * @param pool
	 * @param group
	 * @param node
	 * @throws Exception
	 */
	void addNodeForGroup(String pool, String group, NodeId node) throws Exception;

	/**
	 * 将一个机器节点从分组中删除
	 * 
	 * @param pool
	 * @param group
	 * @param node
	 * @throws Exception
	 */
	void deleteNodeForGroup(String pool, String group, NodeId node) throws Exception;

}
