package com.buxiaoxia.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xw on 2017/3/8.
 * 2017-03-08 13:47
 */
@RestController
@RequestMapping("hello")
public class Controller {

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * 获取服务名“server-01”的所有IP-PORT
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getStr() {
		StringBuilder result = new StringBuilder();
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances("spring-cloud-eureka-client");
		serviceInstances.forEach(serviceInstance ->
				result.append(serviceInstance.getHost())
						.append(":")
						.append(serviceInstance.getPort())
						.append("|"));
		return result.toString();
	}

}
