package com.socket.client;

import java.lang.reflect.Proxy;

/**
 * 
 * @author luoweiyi
 *
 */
public class ServiceProxyFactory {
	
	public Object createProxy(String ip,int port,Class<?> api){
		if(api == null) {
            throw new NullPointerException("api must not be null for ServiceProxyFactory.createProxy()");
        } else {
        	ServiceProxy handler = new ServiceProxy(ip,port,api);
            return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{api}, handler);
        }
	}
	
	public Object createProxy(int port,Class<?> api){
		return createProxy("127.0.0.1",port,api);
	}
	
	public Object createProxy(Class<?> api){
		return createProxy("127.0.0.1",60000,api);
	}
}
