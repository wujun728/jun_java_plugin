/*   
 * Project: OSMP
 * FileName: ConfigServiceManager.java
 * version: V1.0
 */
package com.osmp.config.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.osmp.intf.define.service.ConfigService;

/**
 * 配置服务管理
 * 
 * @author heyu
 *
 */
public class ConfigServiceManager {

	private List<ConfigService> configServices = new ArrayList<ConfigService>();

	public void bind(ConfigService configService, Map<String, String> props) {
		configServices.add(configService);
	}

	public void unbind(ConfigService configService, Map<String, String> props) {
		configServices.remove(configService);
	}

	public void update(String target) {
		for (ConfigService service : configServices) {
			if (service != null) {
				service.update(target);
			}
		}
	}
	
	public void update(String target,String value) {
        for (ConfigService service : configServices) {
            if (service != null) {
                service.update(target,value);
            }
        }
    }

	public Object getData(String target, Map<String, Object> args) {
		for (ConfigService service : configServices) {
			if (null != service) {
				Object obj = service.getData(target, args);
				if (obj != null) {
					return obj;
				}
			}
		}
		return null;
	}
}
