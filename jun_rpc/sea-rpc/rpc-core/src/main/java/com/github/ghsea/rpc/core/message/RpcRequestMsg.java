package com.github.ghsea.rpc.core.message;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 发送给服务端的request消息载体
 * 
 * @author ghsea 2017-4-9下午6:05:40
 */
public class RpcRequestMsg implements Serializable {

	private static final long serialVersionUID = -7739207857763972960L;

	private String requestId;

	/**
	 * 服务端注册的Spring Bean Id
	 */
	private String service;

	private String method;

	private Object[] parameters;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

}
