/*   
 * Project: OSMP
 * FileName: ServiceRegistDao.java
 * version: V1.0
 */
package com.osmp.service.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.osmp.intf.define.interceptor.ServiceInterceptor;
import com.osmp.intf.define.model.InvocationDefine;
import com.osmp.intf.define.service.BaseDataService;
import com.osmp.service.util.ServiceUtil;

/**
 * 
 * Description:服务容器
 * @author: wangkaiping
 * @date: 2016年8月9日 上午10:27:53上午10:51:30
 */
public class ServiceContainer {
	private final static ServiceContainer serviceContainer = new ServiceContainer();
	// 对外接口名称与dataService服务对应关系
	private Map<String, String> dataServiceMapping = new ConcurrentHashMap<String, String>();
	// 对外接口名称与dataService服务对应关系(未配置dataServiceMapping时,默认接口名对应dataService名称)
	private Map<String, String> defaultDataServiceMapping = new HashMap<String, String>();
	// dataService服务对应拦截器链
	private Map<String, List<String>> interceptorMapping = new ConcurrentHashMap<String, List<String>>();
	// dataService服务
	private Map<String, BaseDataService> dataServiceMap = new HashMap<String, BaseDataService>();
	// ServiceInterceptor服务
	private Map<String, ServiceInterceptor> interceptorMap = new HashMap<String, ServiceInterceptor>();

	private ServiceContainer() {
	}

	public final static ServiceContainer getInstance() {
		return serviceContainer;
	}

	public void putDataService(String bundle, String version, String name, BaseDataService dataService) {
		if (name == null || "".equals(name))
			return;
		String allName = ServiceUtil.generateServiceName(bundle, version, name);
		defaultDataServiceMapping.put(name, allName);
		dataServiceMap.put(allName, dataService);
	}

	public void removeDataService(String bundle, String version, String name) {
		if (name == null || "".equals(name))
			return;
		String allName = ServiceUtil.generateServiceName(bundle, version, name);
		defaultDataServiceMapping.remove(name);
		dataServiceMap.remove(allName);
	}

	public void putInterceptor(String name, ServiceInterceptor serviceInterceptor) {
		if (name == null || "".equals(name) || serviceInterceptor == null)
			return;
		interceptorMap.put(name, serviceInterceptor);

	}

	public void removeInterceptor(String name) {
		if (name == null || "".equals(name))
			return;
		interceptorMap.remove(name);
	}

	public InvocationDefine getDefine(String interfaceName) {
		String dataSeriveName = dataServiceMapping.get(interfaceName);
		if (dataSeriveName == null) {
			dataSeriveName = defaultDataServiceMapping.get(interfaceName);
		}
		if (dataSeriveName == null)
			return null;
		BaseDataService ds = dataServiceMap.get(dataSeriveName);
		if (ds == null)
			return null;
		InvocationDefine define = new InvocationDefine();
		define.setDataService(ds);

		List<String> interNames = interceptorMapping.get(dataSeriveName);
		if (interNames == null || interNames.isEmpty())
			return define;
		List<ServiceInterceptor> interceptorChain = new ArrayList<ServiceInterceptor>();
		for (String iName : interNames) {
			if (interceptorMap.get(iName) == null)
				continue;
			interceptorChain.add(interceptorMap.get(iName));
		}
		define.setInterceptors(interceptorChain);

		return define;
	}

	/**
	 * 更新接口服务配置
	 * 
	 * @param interceptorMapping
	 */
	public void updateDataServiceMapping(Map<String, String> dataServiceMapping) {
		this.dataServiceMapping.clear();
		this.dataServiceMapping = dataServiceMapping;
	}

	/**
	 * 更新拦截器配置
	 * 
	 * @param interceptorMapping
	 */
	public void updateInterceptorMapping(Map<String, List<String>> interceptorMapping) {
		this.interceptorMapping.clear();
		this.interceptorMapping = interceptorMapping;
	}

	/**
	 * 通过配置接口更新服务的拦截器链
	 * 
	 * @param opt
	 *            1:更新 2:删除
	 * @param serviceName
	 * @param interceptorMapping
	 */
	public void updateInterceptorMapping(String opt, String serviceName, String interceptorMapping) {
		List<String> intfChain = this.interceptorMapping.get(serviceName);
		if ("1".equals(opt)) {
			if (null == intfChain || intfChain.size() == 0) {
				List<String> chain = new ArrayList<String>();
				chain.add(interceptorMapping);
				this.interceptorMapping.put(serviceName, chain);
			} else {
				intfChain.add(interceptorMapping);
			}
		} else {
			if (null != intfChain) {
				intfChain.remove(interceptorMapping);
			}
		}
	}
}
