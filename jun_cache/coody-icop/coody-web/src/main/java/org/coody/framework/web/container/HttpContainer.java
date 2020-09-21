package org.coody.framework.web.container;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coody.framework.core.container.ThreadContainer;
import org.coody.framework.web.constant.MvcContant;

public class HttpContainer {
	

	public static void setRequest(HttpServletRequest request){
		ThreadContainer.set(MvcContant.REQUEST_WRAPPER, request);
	}
	
	public static HttpServletRequest getRequest(){
		return ThreadContainer.get(MvcContant.REQUEST_WRAPPER);
	}
	
	public static void setResponse(HttpServletResponse response){
		ThreadContainer.set(MvcContant.RESPONSE_WRAPPER, response);
	}
	
	public static HttpServletResponse getResponse(){
		return ThreadContainer.get(MvcContant.RESPONSE_WRAPPER);
	}
}
