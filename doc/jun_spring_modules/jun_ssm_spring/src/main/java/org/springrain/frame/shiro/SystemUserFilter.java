package org.springrain.frame.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springrain.frame.util.GlobalStatic;

/**
 * 系统后台管理用户的过滤器
 * @author caomei
 */

@Component("systemuser")
public class SystemUserFilter extends BaseUserFilter {
	public SystemUserFilter(){
		//跳转到登录界面
	    super.setLoginUrl("/system/login");
	}
	
	@Override
	 protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		 boolean access=super.isAccessAllowed(request, response, mappedValue);
		 
		 HttpServletRequest req=(HttpServletRequest) request;
		 if(!access){//登录失败了,需要设置一下需要跳转的登录URL
			 return access;
		 } 
		 
		
		 Object obj=req.getSession().getAttribute(GlobalStatic.tokenKey);
		 if(obj==null||(!obj.toString().startsWith("system_"))){//tokenKey必须是system_开头
			 request.setAttribute(GlobalStatic.errorTokentoURLKey, GlobalStatic.errorTokentoURL);
		     return false;
		 }
	
		String token=obj.toString();

		String userToken=req.getParameter(GlobalStatic.tokenKey);
		if(token.equals(userToken)){
			 return true;
		}
		
		request.setAttribute(GlobalStatic.errorTokentoURLKey, GlobalStatic.errorTokentoURL);
		
		return false;
		
	}

}
