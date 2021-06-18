package org.smartboot.service.util;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smartboot.sosa.core.rmi.RmiServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现RPC服务的抽象类
 * 
 * @author Wujun
 * @version AbstractRpcService.java, v 0.1 2016年6月23日 下午6:12:27 Seer Exp.
 */
public abstract class AbstractRpcService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(AbstractRpcService.class);

	@Autowired
	private RmiServer rmiServer;

	@PostConstruct
	public void init() {
		Class<?>[] serviceName = getRpcInterfaces();
		AssertUtils.isTrue(ArrayUtils.isNotEmpty(serviceName), "未定义服务名");
		for (Class<?> c : serviceName) {
			rmiServer.publishService(c.getName(), this);
			LOGGER.info("publish service " + c.getName() + " success!");
		}
	}

	/**
	 * 发布的RPC服务接口列表
	 * 
	 * @return
	 */
	public abstract Class<?>[] getRpcInterfaces();
}
