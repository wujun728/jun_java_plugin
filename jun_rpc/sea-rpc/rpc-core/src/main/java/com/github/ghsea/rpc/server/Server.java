package com.github.ghsea.rpc.server;

import com.github.ghsea.rpc.server.registry.ServiceRegistry;

/**
 * Server寄宿于Tomcat
 * 
 * @author ghsea 2017-2-11上午10:58:59
 */
interface Server extends ServiceRegistry {

	/**
	 * 初始化服务器资源，服务器启动
	 * 
	 * @throws Exception
	 */
	void start();

	/**
	 * 释放服务器资源，服务器shutdown
	 * 
	 * @throws Exception
	 */
	void shutdown();

	String getHost();

	int getPort();
}
