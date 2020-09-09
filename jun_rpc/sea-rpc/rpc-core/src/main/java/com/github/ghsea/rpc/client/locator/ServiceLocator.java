package com.github.ghsea.rpc.client.locator;

import com.github.ghsea.rpc.common.modle.ServiceProfile;

public interface ServiceLocator {
	/**
	 * 获取一个服务
	 * 
	 * @return
	 */
	ServiceProfile locateService();
}
