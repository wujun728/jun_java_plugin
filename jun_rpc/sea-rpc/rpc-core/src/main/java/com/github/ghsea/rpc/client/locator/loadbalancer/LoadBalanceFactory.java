package com.github.ghsea.rpc.client.locator.loadbalancer;

import com.github.ghsea.rpc.common.modle.ReferenceProfile;
import com.google.common.base.Preconditions;

public class LoadBalanceFactory {

	private LoadBalanceFactory() {

	}

	/**
	 * 
	 * @param poolName
	 *            pool名称
	 * @param groupName
	 *            分组名称
	 * @param loadBalanceType
	 *            负载均衡器算法
	 * @return
	 */
	public static LoadBalancer getLoadBalance(ReferenceProfile profile) {
		Preconditions.checkNotNull(profile);
		LoadBalanceTypeEnum loadBalanceType = profile.getLoadBalanceType();
		switch (loadBalanceType) {
		case ROBIN_ROUND:
			return new RobinroundLoadBalancer(profile);
		default:
			throw new RuntimeException("not supported loadbalance type:" + loadBalanceType);
		}
		
	}

}
