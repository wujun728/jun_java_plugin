package org.springrain.frame.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springrain.frame.util.CookieUtils;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.InputSafeUtils;

/**
 * 前台用户的 过滤器，主要设置登陆界面
 * @author caomei
 *
 */


@Component("frontuser")
public class FrontUserFilter extends BaseUserFilter {
	
	public FrontUserFilter(){
		//跳转到登录界面
		super.setLoginUrl("/login");
	}

	@Override
	 protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		 boolean access=super.isAccessAllowed(request, response, mappedValue);
		 HttpServletRequest req=(HttpServletRequest) request;
		 if(!access){//登录失败了,需要设置一下需要跳转的登录URL
			 String siteId=InputSafeUtils.substringByURI(req.getRequestURI(), "/s_");
			 
			 
			 if(StringUtils.isBlank(siteId)){//URL中没有siteId,从cookie中取值
				 siteId = CookieUtils.getCookieValue(req, GlobalStatic.springraindefaultSiteId);
			 }
			 
			 if(StringUtils.isNotBlank(siteId)){
				 request.setAttribute(GlobalStatic.customLoginURLKey, "/f/"+siteId+"/login");
			 }
			
			 return access;
		 } 
		 
		 
		Object obj=req.getSession().getAttribute(GlobalStatic.tokenKey);
		String token=obj.toString();
		
		String userToken=req.getParameter(GlobalStatic.tokenKey);
		if(token.equals(userToken)){
			 return true;
		}
		
		request.setAttribute(GlobalStatic.errorTokentoURLKey, GlobalStatic.errorTokentoURL);
		
		return false;
	}

}


