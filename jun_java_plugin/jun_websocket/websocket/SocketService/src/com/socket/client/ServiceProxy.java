package com.socket.client;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * The proxy for service.
 * @author luo
 *
 */
public class ServiceProxy implements InvocationHandler, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6826716497369894078L;
	
	private String ip;
	
	private int port;
	
	private Class<?> api;
	
	public ServiceProxy(String ip,int port,Class<?> api){
		this.ip = ip;
		this.port = port;
		this.api = api;
	}

	@Override
	public Future invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Future future = new Future();
		Request request = new Request();
		//msg.setReturnType(User.class.getName());
		request.setService(api.getName());
		request.setMethodName(method.getName());
		request.setParams(args);

		//send the request to the server
		new Client(ip,port,request,future).sendRequest();
		
		//System.out.println("get value from: serverIp="+ip+"   port="+port);
		return future;
	}

}
