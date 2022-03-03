package org.springrain.frame.shiro;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.JsonUtils;
import org.springrain.frame.util.ReturnDatas;


/**
 * 处理登录重定向并验证token,防御CSRF漏洞
 * @author caomei
 *
 */
public class BaseUserFilter extends UserFilter {
	
	/*
	@Override
	 protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		 boolean access=super.isAccessAllowed(request, response, mappedValue);
		 if(!access){
			 return access;
		 }
		 
		 //已经登录用户,验证Referer
		 HttpServletRequest req=(HttpServletRequest) request;
		 String referer=req.getHeader("Referer");
		 //System.out.println(referer+":"+req.getHeader("X-Requested-With"));
		 if(StringUtils.isBlank(referer)||(!referer.contains(request.getServerName()))){
			 Subject subject = SecurityUtils.getSubject();
		        if (subject != null) {           
		            subject.logout();
		        }
			 return false;
		 }
		
		 
		 
		 return access;
	 }
	 */
	
	@Override
	  protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		     HttpServletRequest req=(HttpServletRequest) request;
		     HttpServletResponse res=(HttpServletResponse) response;
		     
		     //先处理
		     
		     
		     boolean isauth=true;
		     String jsonStatusCode="relogin";
		     String jsonMessage="登录超时,请重新登录!";
		     
		     Object errorTokentoURLKey=request.getAttribute(GlobalStatic.errorTokentoURLKey);
		     if(errorTokentoURLKey!=null){
		    	 isauth=false;
		    	 jsonStatusCode="errortoken";
			     jsonMessage="token错误";
			     request.removeAttribute(GlobalStatic.errorTokentoURLKey);
		     }
		     
		     
		     
		     
		     if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {// ajax请求,返回JSON
		    	 res.setCharacterEncoding(GlobalStatic.defaultCharset);
		    	 res.setContentType("application/json;charset="+GlobalStatic.defaultCharset);
			     PrintWriter out = res.getWriter();
			     ReturnDatas returnDatas=ReturnDatas.getErrorReturnDatas();
			     returnDatas.setStatusCode(jsonStatusCode);
			     returnDatas.setMessage(jsonMessage);
			     out.println(JsonUtils.writeValueAsString(returnDatas));
			     out.flush();
			     out.close();
			     return false;
			    }
		     
		     if(!isauth){//没有权限,处理tokenKey错误
		    	 WebUtils.issueRedirect(request, response, GlobalStatic.errorTokentoURL);
		    	 return false;
		     }
		     
		     
		     
		     
		     //跳转前 清除 参数
		     request.removeAttribute(GlobalStatic.tokenKey);
		     //正常http请求
	         String loginUrl= getLoginUrl();
	         
	         //程序控制自定义的登录地址
	         if(request.getAttribute(GlobalStatic.customLoginURLKey)!=null){//有自定义的登录地址
	        	 loginUrl=request.getAttribute(GlobalStatic.customLoginURLKey).toString();
	        	//跳转前 清除 参数
			     request.removeAttribute(GlobalStatic.customLoginURLKey);
	         }
	         
	         
	         StringBuffer url=req.getRequestURL();
	         //String query=req.

	 		Map<String,String[]> map=request.getParameterMap();  
	 	    Set<Map.Entry<String,String[]>> keSet=map.entrySet();  
	 	    
	 	    for(Iterator<Entry<String, String[]>> itr=keSet.iterator();itr.hasNext();){  
	 	    	
	 	        Map.Entry<String,String[]> me=itr.next();  
	 	        String key=me.getKey();  
	 	        if(GlobalStatic.tokenKey.equals(key)){
	 	        	continue;
	 	        }
	 	        
	 	        String[] value=me.getValue(); 
	 	        if(value==null||value.length==0){
	 	        	continue;
	 	        }else if(value.length==1){
	 	        	if(url.indexOf("?")<0){
	 	        		url.append("?");
	 	        	}else{
	 	        		url.append("&");
	 	        	}
	 	        	
	 	        	url.append(key).append("=").append(value[0]);
	 	        		
	 	        }else{
	 	        	if(url.indexOf("?")<0){
	 	        		url.append("?");
	 	        	}else{
	 	        		url.append("&");
	 	        	}
	 	        	
	 	        	url.append(key).append("=").append(value);
	 	        }
	 	        
	 	  
	 	     }  
	 		
	        
	         Map<String,String> parMap=new HashMap<String,String>();
	         parMap.put("gotourl", url.toString());
		
	         WebUtils.issueRedirect(request, response, loginUrl,parMap);
	         
	         return false;
	    }
	
	
	
	
	
	

}
