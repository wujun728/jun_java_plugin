package com.github.ghsea.rpc.client.locator.loadbalancer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.CollectionUtils;

import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ReferenceProfile;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.github.ghsea.rpc.core.exception.RpcProviderNotExistException;

public abstract class AbstractLoadBalancer implements LoadBalancer {

	protected volatile List<ServiceProfile> serviceListOfGroupSensitive;

	private volatile List<ServiceProfile> serviceListOfNoGroupSensitive;

	private Lock lock = new ReentrantLock();

	protected ReferenceProfile reference;

	public AbstractLoadBalancer(ReferenceProfile reference) {
		this.reference = reference;
		serviceListOfGroupSensitive = new ArrayList<ServiceProfile>();
	}

	public ServiceProfile select() {
		if (CollectionUtils.isEmpty(serviceListOfGroupSensitive)) {
			throw new RpcProviderNotExistException(this.reference.toString());
		}

		return doSelect(serviceListOfGroupSensitive);
	}

	abstract protected ServiceProfile doSelect(final List<ServiceProfile> serviceProfile);

	@Override
	public void init(List<ServiceProfile> serviceOfGroupNoSensitive, List<NodeId> nodeListByGroup) {
		if (lock.tryLock()) {
			try {
				if (CollectionUtils.isEmpty(serviceOfGroupNoSensitive)) {
					return;
				}

				List<ServiceProfile> spList = new ArrayList<ServiceProfile>(serviceOfGroupNoSensitive.size());
				for (ServiceProfile service : serviceOfGroupNoSensitive) {
					spList.add(service);
					if (nodeListByGroup.contains(service.getNodeId())) {
						serviceListOfGroupSensitive.add(service);
					}
				}
				this.serviceListOfNoGroupSensitive = spList;
			} finally {
				lock.unlock();
			}
		}
	}

	@Override
	public void update(List<NodeId> validNodeList) {
		if (lock.tryLock()) {
			try {
				init(serviceListOfNoGroupSensitive, validNodeList);
			} finally {
				lock.unlock();
			}
		}
	}

}
