package com.socket.client;

import java.io.Serializable;
import java.util.Arrays;

/**
 * When client call a service,the client will send a request to server.
 * @author luoweiyi
 *
 */
public class Request implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4841618514436609238L;

	/**
	 * service name
	 */
	private String service;
	
	/**
	 * method name
	 */
	private String methodName;
	
	/**
	 * method params
	 */
	private Object[] params;
	
	/**
	 * method return type
	 */
	private String returnType;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Override
	public String toString() {
		return "Request [service=" + service + ", params="
				+ Arrays.toString(params) + ", returnType=" + returnType + "]";
	}
	
}
