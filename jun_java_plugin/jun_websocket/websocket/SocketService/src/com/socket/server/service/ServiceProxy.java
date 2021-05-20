package com.socket.server.service;

import java.lang.reflect.Method;

import com.socket.server.Response;
import com.socket.util.Constants;

/**
 * 
 * @author luoweiyi
 *
 */
public class ServiceProxy {
	
	public Object doService(Class<?> serviceClass,Method method,Object[] args,Response response){
		
		Object res = null;
		try {
			//start call service
			Object obj = serviceClass.newInstance();
			res = method.invoke(obj, args);
			
			//set result to response
			response.setStatusAndValue(Constants.SUCCESS,res);
		} catch (Exception e) {
			response.setStatusAndValue(Constants.FAILURE,null,"Call method failure!");
		}
		return res;
	}
}
