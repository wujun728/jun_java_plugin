package com.socket.server.service;

import java.lang.reflect.Method;

import com.socket.server.Response;

/**
 * 执行service线程
 * @author luoweiyi
 *
 */
public class ServiceThread extends Thread{
	
	private Response response;
	
	private String serviceImpl;
	
	private String methodStr;
	
	private Object[] args;
	
	/**
	 * @param serviceImpl
	 * @param methodStr
	 * @param args
	 * @param response
	 */
	public ServiceThread(String serviceImpl,String methodStr,Object[] args,Response response){
		this.serviceImpl = serviceImpl;
		this.methodStr = methodStr;
		this.args = args;
		this.response = response;
	}
	
	public void run(){
		
		try {
			Class<?> serverCls = Class.forName(serviceImpl);
			Method method = null;
			if(null != args){
				Class<?>[] paramsCls = new Class<?>[args.length];
				for(int i = 0; i < args.length; i++)
					paramsCls[i] = args[i].getClass();
				method = serverCls.getMethod(methodStr,paramsCls);
			}else{
				method = serverCls.getMethod(methodStr);
			}
			
			ServiceProxy serviceProxy = new ServiceProxy();
			serviceProxy.doService(serverCls, method, args,response);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
