package org.coody.framework.rcc.entity;

import org.coody.framework.core.entity.BaseModel;

@SuppressWarnings("serial")
public class RccInstance extends BaseModel{

	/**
	 * ip地址
	 */
	private String host;
	/**
	 * 端口
	 */
	private Integer port;
	/**
	 * 权重
	 */
	private Integer pr;
	
	
	

	public Integer getPr() {
		return pr;
	}

	public void setPr(Integer pr) {
		this.pr = pr;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	
	
}
