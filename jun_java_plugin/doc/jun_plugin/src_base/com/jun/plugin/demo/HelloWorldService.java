package com.jun.plugin.demo;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldService implements Servlet{
	
	public void init(ServletConfig arg0) throws ServletException {
		
	}

	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		/**第一个参数*/
		//接口：javax.servlet.ServletRequest
		//实现类：org.apache.catalina.connector.RequestFacade
		/*
		 * 关系：
		 *   * public class RequestFacade implements HttpServletRequest {
		 *   * public interface HttpServletRequest extends ServletRequest {
		 * 
		 *   总结： RequestFacade --> HttpServletRequest  --> ServletRequest
		 *   
		 *   ************
		 *   ServletRequest req = new RequestFacade();
		 *   HttpServletRequest http = new RequestFacade();
		 *   
		 *   HttpServletRequest http2 = (HttpServletRequest)req;
		 *   
		 */
		
//		HttpServletRequest http = (HttpServletRequest)req;
		
//		System.out.println("request : " + req);
		
		/**第二个参数*/
		System.out.println("resp --> " + resp);
		//接口：javax.servlet.ServletResponse
		//实现类：org.apache.catalina.connector.ResponseFacade
		
		/* 关系：
		 * 	 * public class ResponseFacade implements HttpServletResponse {
		 *   * public interface HttpServletResponse extends ServletResponse {
		 *   
		 *   * 总结：ResponseFacade  --> HttpServletResponse  --> ServletResponse
		 */
		HttpServletResponse response = (HttpServletResponse)resp;
		
	}

	public void destroy() {
		
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	public String getServletInfo() {
		return null;
	}



}
