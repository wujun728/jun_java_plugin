/*   
 * Project: OSMP
 * FileName: WebServiceFactoryImpl.java
 * version: V1.0
 */
package com.osmp.tools.ws.impl;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.osmp.tools.ws.WebServiceFactory;

/**
 * Description:webService代理工厂实现类
 * 
 * @author: wangkaiping
 * @date: 2014年9月11日 上午11:19:26
 */

public class WebServiceFactoryImpl implements WebServiceFactory, InitializingBean {
	private static final Log logger = LogFactory.getLog(WebServiceFactoryImpl.class);
	private static Lock lock = new ReentrantLock();
	private static Map<String, Object> proxyServiceInstances;

	@Override
	public <T extends Object> T getClientProxy(String address, Class<T> serviceClass) {
		Assert.notNull(serviceClass, "Service class is must not null.");
		Assert.isTrue(StringUtils.hasLength(address), "Service address is must not null or empty.");
		String instanceKey = String.valueOf(serviceClass.hashCode() + address.hashCode());
		logger.info("Generate key of the proxy service instances map is [" + instanceKey + "] address : " + address);

		T cachInstance = null;
		if (getProxyServiceInstances().containsKey(instanceKey)) {
			cachInstance = serviceClass.cast(getProxyServiceInstances().get(instanceKey));
		}
		if (null == cachInstance) {
			try {
				lock.lock();
				cachInstance = serviceClass.cast(getProxyServiceInstances().get(instanceKey));
				if (null == cachInstance) {
					JaxWsProxyFactoryBean proxyFactoryBean = new JaxWsProxyFactoryBean();
					proxyFactoryBean.setServiceClass(serviceClass);
					proxyFactoryBean.setAddress(address);
					cachInstance = proxyFactoryBean.create(serviceClass);

					Client client = ClientProxy.getClient(cachInstance);
					if (null != client) {
						logger.info("=====>>Webservice client info : " + client.toString());
						HTTPConduit conduit = (HTTPConduit) client.getConduit();
						logger.info("=====>>Before set the receive timeout : "
								+ conduit.getClient().getReceiveTimeout());
						conduit.getClient().setReceiveTimeout(0);
						logger.info("=====>>After set the receive timeout : " + conduit.getClient().getReceiveTimeout());
					}

					getProxyServiceInstances().put(instanceKey, cachInstance);
				}
			} finally {
				lock.unlock();
			}
		}
		logger.debug("Map of the proxy service instances : " + getProxyServiceInstances());

		return cachInstance;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("=====>>Before - Service instance doing initializing : " + proxyServiceInstances);
		if (null != proxyServiceInstances && !proxyServiceInstances.isEmpty()) {
			proxyServiceInstances.clear();
		}
		logger.info("=====>>After - Service instance doing initializing : " + proxyServiceInstances);
	}

	protected Map<String, Object> getProxyServiceInstances() {
		if (null == proxyServiceInstances) {
			try {
				lock.lock();
				if (null == proxyServiceInstances) {
					proxyServiceInstances = new Hashtable<String, Object>();
				}
			} finally {
				lock.unlock();
			}
		}
		return proxyServiceInstances;
	}
}
