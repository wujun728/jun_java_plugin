/*   
 * Project: OSMP
 * FileName: ServiceFactoryImpl.java
 * version: V1.0
 */
package com.osmp.service.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.osmp.intf.define.factory.ServiceFactory;
import com.osmp.intf.define.model.InvocationDefine;
import com.osmp.service.registration.ServiceContainer;

/**
 * dataService服务提供工厂
 * 
 * @author heyu
 *
 */
public class ServiceFactoryImpl implements ServiceFactory {

	private Map<String, String> map = new ConcurrentHashMap<String, String>();

	// 获取服务
	public InvocationDefine getInvocationDefine(String name) {
		return ServiceContainer.getInstance().getDefine(name);
	}

	@Override
	public Map<String, String> getAllService() {
		return this.map;
	}

	/**
	 * 添加服务
	 * 
	 * @param path
	 * @param data
	 */
	public void addService(String path, String data) {
		this.map.put(path, data);
	}

	/**
	 * 移除服务
	 * 
	 * @param path
	 */
	public void removeService(String path) {
		this.map.remove(path);
	}

}
