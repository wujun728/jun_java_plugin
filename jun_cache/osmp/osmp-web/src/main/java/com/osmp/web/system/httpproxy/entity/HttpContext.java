package com.osmp.web.system.httpproxy.entity;

import java.util.Map;

/**
 * Description: HTTP请求上下文
 * 
 * @author: wangkaiping
 * @date: 2015年4月17日 下午2:28:42
 */
public class HttpContext {

	/**
	 * 客户端IP
	 */
	private String ip;

	/**
	 * 访问源 根据项目划分
	 */
	private String source;

	/**
	 * 服务名称
	 */
	private String service;

	/**
	 * 请求的URL地址
	 */
	private String url;

	/**
	 * 请求的参数
	 */
	private Map<String, Object> argsMap;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getArgsMap() {
		return argsMap;
	}

	public void setArgsMap(Map<String, Object> argsMap) {
		this.argsMap = argsMap;
	}

}
