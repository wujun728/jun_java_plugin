package com.github.ghsea.rpc.client.locator.loadbalancer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;

import com.github.ghsea.rpc.common.modle.ReferenceProfile;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.github.ghsea.rpc.core.exception.RpcProviderNotExistException;

/**
 * 一个LoadBalance对应 到1个ReferenceProfile
 * 
 * @author ghsea 2017-3-19下午4:14:19
 */
public class RobinroundLoadBalancer extends AbstractLoadBalancer {

	// key=serviceName
	private ConcurrentHashMap<String, AtomicInteger> service2Counter = new ConcurrentHashMap<String, AtomicInteger>();
	
	private final int INIT_CNT = -1;

	private String poolName;

	protected RobinroundLoadBalancer(ReferenceProfile reference) {
		super(reference);
//		Preconditions.checkArgument(StringUtils.isNotBlank(poolName));
//		this.poolName = poolName;
	}

	@Override
	protected ServiceProfile doSelect(List<ServiceProfile> serviceProfile) {
		if (CollectionUtils.isEmpty(serviceProfile)) {
			throw new RpcProviderNotExistException(String.format("Pool: %s , Version: %s , Group :%s", poolName,
					reference.getVersion(), reference.getGroup()));
		}

		String serviceName = reference.getService();
		AtomicInteger count = service2Counter.get(reference.getService());
		if (count == null) {
			synchronized (this) {
				if (count == null) {
					count = new AtomicInteger(INIT_CNT);
				}
			}
			service2Counter.putIfAbsent(serviceName, count);
		}

		if (count.get() < 0) {
			synchronized (this) {
				if (count.get() < 0) {
					count.set(INIT_CNT);
				}
			}
		}

		int idx = count.incrementAndGet() % serviceProfile.size();

		return serviceProfile.get(idx);
	}

}
