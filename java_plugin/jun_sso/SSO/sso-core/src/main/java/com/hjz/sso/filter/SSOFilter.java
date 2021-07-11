package com.hjz.sso.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hjz.sso.utils.RestTemplateUtil;

public class SSOFilter implements Filter{

	public static Logger logger = LoggerFactory.getLogger(SSOFilter.class);
    
	private String SSO_SERVER_URL;
	private String SSO_SERVER_VERIFY_URL;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SSO_SERVER_URL = filterConfig.getInitParameter("SSO_SERVER_URL");
		SSO_SERVER_VERIFY_URL = filterConfig.getInitParameter("SSO_SERVER_VERIFY_URL");
		if(SSO_SERVER_URL == null) logger.error("SSO_SERVER_URL is null.");
		if(SSO_SERVER_VERIFY_URL == null) logger.error("SSO_SERVER_VERIFY_URL is null.");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
		 HttpServletResponse response = (HttpServletResponse) res;
		 //请求中带有token，去sso-server验证token是否有效
		 String authority = null;
		 if(request.getParameter("token") != null) {
		    boolean verifyResult = this.verify(request, SSO_SERVER_VERIFY_URL, request.getParameter("token"));
		    if (verifyResult) {
		    	chain.doFilter(req, res);
		        return;
		    } else {
		    	authority = "token->" + request.getParameter("token") + " is invalidate.";
		    }
		 }
		 
		 HttpSession session = request.getSession();
		 if (session.getAttribute("login") != null && (boolean)session.getAttribute("login") == true) {
			chain.doFilter(req, res);
		    return;
		 }
		 //跳转至sso认证中心
		 String callbackURL = request.getRequestURL().toString();
		 StringBuilder url = new StringBuilder();
		 url.append(SSO_SERVER_URL).append("?callbackURL=").append(callbackURL);
		 if(authority != null) {
			 url.append("&authority=").append(authority);
		 }
		 response.sendRedirect(url.toString());
	}
	
	private boolean verify(HttpServletRequest request, String verifyUrl, String token) {
		String result = RestTemplateUtil.get(request, verifyUrl + "?token=" + token, null);
		JSONObject ret = JSONObject.parseObject(result);
		if("success".equals(ret.getString("code"))) {
			return true;
		}
		logger.error(request.getRequestURL().toString() + " : " + ret.getString("msg"));
		return false;
	}

	@Override
	public void destroy() {
	}

}
