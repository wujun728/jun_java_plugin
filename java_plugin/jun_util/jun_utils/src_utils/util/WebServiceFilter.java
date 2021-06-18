/**
 * Program  : WebServiceFilter.java
 * Author   : zengtao
 * Create   : 2009-3-3 ÏÂÎç04:54:16
 *
 * Copyright 2006 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.apps.payment.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @author   zengtao
 * @version  1.0.0
 * @2009-3-3 ÏÂÎç04:54:16
 */
public class WebServiceFilter implements Filter {
	private String ipaddress;
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	String 		host=	request.getRemoteHost();
		if(host.equals("127.0.0.1")||host.equals("localhost")||ipaddress.indexOf(host)!=-1){
			 chain.doFilter(request, response);
		}else
		{
			response.getWriter().write("error!! not allowed");
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		 ipaddress=filterConfig.getInitParameter("ip");
	}

}

