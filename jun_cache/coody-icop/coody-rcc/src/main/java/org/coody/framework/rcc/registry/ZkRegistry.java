package org.coody.framework.rcc.registry;

import java.util.List;

import org.coody.framework.rcc.entity.RccInstance;
import org.coody.framework.rcc.registry.iface.RegistryFace;

/**
 * Zookeeper注册中心
 * @author Coody
 *
 */
public class ZkRegistry implements RegistryFace{

	@Override
	public List<RccInstance> getRccInstances(String methodKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMethods(String host, Integer port) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RccInstance getRccInstance(String methodKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean register(String host, String port, String methodKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
