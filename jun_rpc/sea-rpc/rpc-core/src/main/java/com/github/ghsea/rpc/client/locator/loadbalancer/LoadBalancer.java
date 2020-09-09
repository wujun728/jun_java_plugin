package com.github.ghsea.rpc.client.locator.loadbalancer;

import java.util.List;

import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ServiceProfile;

public interface LoadBalancer {

	/**
	 * 
	 * @param serviceOfGroupNoSensitive
	 *            对分组不敏感的服务列表,分组所对应的NodeId List
	 * @param nodeListByGroup
	 */
	void init(List<ServiceProfile> serviceOfGroupNoSensitive, List<NodeId> nodeListByGroup);

	/**
	 * 根据分组所对应的nodeList更新能给该分组提供服务的服务列表
	 * 
	 * @param nodeListByGroup
	 */
	void update(List<NodeId> nodeListByGroup);

	/**
	 * 选择一个服务节点
	 * @return
	 */
	ServiceProfile select();
}
