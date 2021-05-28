package com.ibeetl.admin.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.ibeetl.admin.core.conf.MVCConf;

/**
 *  保留用户会话，以方便在业务代码任何地方调用
 *  {@link MVCConf}
 * @author Wujun
 *
 */
@Component
public class HttpRequestLocal {
	
	public  HttpRequestLocal(){
		
	}
	
	  private static final ThreadLocal<HttpServletRequest> requests =
		         new ThreadLocal<HttpServletRequest>() {
		             @Override protected HttpServletRequest initialValue() {
		                 return null;
		         }
		     };
		     
	 public  Object getSessionValue(String attr){
		 return  requests.get().getSession().getAttribute(attr);
	 }
	 
	 public  void setSessionValue(String attr,Object obj){
		   requests.get().getSession().setAttribute(attr,obj);
	 }
	 
	 
	 
	 public  Object getRequestValue(String attr){
		 return  requests.get().getAttribute(attr);
	 }
	 
	 public String getRequestURI(){
		 return  requests.get().getRequestURI();
	 }
	 
	 public String getRequestIP(){
		 return getIpAddr(requests.get());
	 }
	 
	 public  void set(HttpServletRequest request){
		 requests.set(request);
	 }
	 
	 /** 
	     * 获取当前网络ip 
	     * @param request 
	     * @return 
	     */  
	    public String getIpAddr(HttpServletRequest request){  
	        String ipAddress = request.getHeader("x-forwarded-for");  
	            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	                ipAddress = request.getHeader("Proxy-Client-IP");  
	            }  
	            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
	            }  
	            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	                ipAddress = request.getRemoteAddr();  
	                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
	                    //根据网卡取本机配置的IP  
	                    InetAddress inet=null;  
	                    try {  
	                        inet = InetAddress.getLocalHost();  
	                    } catch (UnknownHostException e) {  
	                        e.printStackTrace();  
	                    }  
	                    ipAddress= inet.getHostAddress();  
	                }  
	            }  
	            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
	            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
	                if(ipAddress.indexOf(",")>0){  
	                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
	                }  
	            }  
	            return ipAddress;   
	    }
}
