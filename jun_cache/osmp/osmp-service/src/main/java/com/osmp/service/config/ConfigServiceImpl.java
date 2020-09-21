/*   
 * Project: OSMP
 * FileName: ConfigServiceImpl.java
 * version: V1.0
 */
package com.osmp.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.intf.define.service.ConfigService;
import com.osmp.intf.define.service.adapter.UpdateNoValConfigServiceAdapter;
import com.osmp.service.manager.ServiceConfigManager;

/**
 * 配置信息
 * 
 * @author heyu
 *
 */
public class ConfigServiceImpl extends UpdateNoValConfigServiceAdapter implements InitializingBean {
	private Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

	private ServiceConfigManager serviceConfigManager;

	public void setServiceConfigManager(ServiceConfigManager serviceConfigManager) {
		this.serviceConfigManager = serviceConfigManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(serviceConfigManager, "serviceConfigManager未初始化");
	}

	@Override
	public void update(String target) {
		if (ConfigService.DATASERVICE_CONFIG.equals(target)) {
			logger.info("更新接口服务配置...");
			serviceConfigManager.updateDataServiceMapping();
		} else if (ConfigService.INTERCEPTOR_CONFIG.equals(target)) {
			logger.info("更新接口服务拦截器配置...");
			serviceConfigManager.updateInterceptorMapping();
		}

	}

}
