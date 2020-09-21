/*   
 * Project: OSMP
 * FileName: ConfigIntceptorImpl.java
 * version: V1.0
 */
package com.osmp.service.config;

import java.util.Map;

import com.osmp.intf.define.service.ConfigService;
import com.osmp.service.registration.ServiceContainer;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2015年7月10日 下午3:53:47
 */
public class ConfigIntceptorImpl implements ConfigService {

	@Override
	public void update(String target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String target, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getData(String target, Map<String, Object> args) {
		if (ConfigService.INTERCEPTOR_CONFIG.equals(target)) {
			String[] opt = (String[]) args.get("opt");
			String[] serviceName = (String[]) args.get("serviceName");
			String[] interceptor = (String[]) args.get("interceptor");
			ServiceContainer.getInstance().updateInterceptorMapping(opt[0], serviceName[0], interceptor[0]);
		}
		return "ok";
	}

}
