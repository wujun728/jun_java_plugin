package com.socket.server.config;

import java.util.Map;

public interface ServerConfig {
	
	/**
	 * Map<serviceApi,serviceImpl>
	 * @return
	 */
	public Map<String,String> getServiceMap();

}
