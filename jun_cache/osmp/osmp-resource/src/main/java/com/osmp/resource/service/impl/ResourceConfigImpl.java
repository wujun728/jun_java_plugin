/*   
 * Project: OSMP
 * FileName: ResourceConfigImpl.java
 * version: V1.0
 */
package com.osmp.resource.service.impl;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.intf.define.service.ResourceConfig;

/**
 * Description:基础组件-加载资源文件接口
 * 
 * @author: wangkaiping
 * @date: 2014年8月22日 上午10:51:30
 */
public class ResourceConfigImpl implements ResourceConfig, ManagedService, InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceConfigImpl.class);
	private static Properties config;

	@Override
	public String getProperty(String key) {
		return config.getProperty(key);
	}

	@Override
	public void updated(@SuppressWarnings("rawtypes") Dictionary properties) throws ConfigurationException {
		if (null == properties || properties.isEmpty()) {
			logger.warn("Failed to read the INMP configuration, config properties is null.");
			return;
		}
		logger.info("osmp config data has updated.");
		this.getConfig().clear();
		logger.info("======>>Properties keys : " + properties.size() + "\t" + properties.keys());
		Enumeration<?> keys = properties.keys();
		while (keys.hasMoreElements()) {
			String key = String.valueOf(keys.nextElement());
			String value = String.valueOf(properties.get(key));
			logger.info("osmp properties item : " + key + "\t" + value);
			this.getConfig().put(key, value);
		}
		logger.info("Successed to read the osmp configuration, the new values :\n" + getConfig());
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		BundleContext bandleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		ServiceReference refrenceByConfigAdmin = bandleContext.getServiceReference(ConfigurationAdmin.class.getName());
		Assert.notNull(refrenceByConfigAdmin, "Failed to get the configuration admin, it is null.");
		ConfigurationAdmin configAdmin = (ConfigurationAdmin) bandleContext.getService(refrenceByConfigAdmin);
		Configuration config = configAdmin.getConfiguration(osmp_PID);
		Assert.notNull(config, "Could not find the PID configuration information, PID : " + osmp_PID);
		this.updated(config.getProperties());
	}
	
	private Properties getConfig() {
		if (null == config) {
			config = new Properties();
		}
		return config;
	}

}
