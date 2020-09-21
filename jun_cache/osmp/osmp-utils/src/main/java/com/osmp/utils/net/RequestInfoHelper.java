/*   
 * Project: OSMP
 * FileName: RequestInfoHelper.java
 * version: V1.0
 */
package com.osmp.utils.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestInfoHelper {
	private static final Logger logger = LoggerFactory
			.getLogger(RequestInfoHelper.class);
    
	/**
	 * 获取http请求远程客户端ip地址
	 * 
	 * @param request
	 * @return
	 */
    public final static String getRemoteIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");   
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))   
            ip = request.getHeader("Proxy-Client-IP");   
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))   
            ip = request.getHeader("WL-Proxy-Client-IP");   
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))   
            ip = request.getHeader("HTTP_CLIENT_IP");   
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))   
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))   
            ip = request.getRemoteAddr();   
  
        if ("0:0:0:0:0:0:0:1".equals(ip.trim()))   
            ip = "server";   
  
        return ip;   
    }

	/**
	 * 获取本机IP
	 * 
	 * @return
	 */
	public final static String getLocalIp() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip;
		} catch (UnknownHostException e) {
			logger.error("get local ip address fail...", e);
			return "";
		}

	}
}
