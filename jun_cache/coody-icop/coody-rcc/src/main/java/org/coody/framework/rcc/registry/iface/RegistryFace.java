package org.coody.framework.rcc.registry.iface;

import java.util.List;

import org.coody.framework.rcc.entity.RccInstance;

public interface RegistryFace {

	/**
	 * 根据方法签名获得Rcc实例
	 * @param methodKey
	 * @return
	 */
	public List<RccInstance> getRccInstances(String methodKey);
	
	/**
	 * 根据ip+端口获得支持的方法签名
	 */
	public List<String> getMethods(String host,Integer port);
	
	/**
	 * 根据方法签名获得负载最低Rcc实例
	 */
	public RccInstance getRccInstance(String methodKey);
	
	/**
	 * 注册服务
	 */
	public boolean register(String host,String port,String methodKey);
}
