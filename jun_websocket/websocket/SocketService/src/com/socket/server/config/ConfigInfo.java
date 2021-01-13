package com.socket.server.config;

import java.util.Map;

public class ConfigInfo {
	
	private String ip = "127.0.0.1";
	
	private int port;
	
	private int maxConnection = 10;
	
	private int maxThreads = 2;
	
	private Long timeout = -1L;
	
	private Map<String,String> serviceMap;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxConnection() {
		return maxConnection;
	}

	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public Map<String, String> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, String> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "ConfigInfo [ip=" + ip + ", port=" + port + ", maxConnection="
				+ maxConnection + ", maxThreads=" + maxThreads + ", timeout="
				+ timeout + ", serviceMap=" + serviceMap + "]";
	}
	
	
}
