package org.springrain.weixin.shirofilter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springrain.frame.util.InputSafeUtils;
import org.springrain.system.service.IUserService;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;

@Component("wxmpautologin")
public class WxMpAutoLoginFilter extends OncePerRequestFilter {
	private final   Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Resource
	private IWxMpConfigService wxMpConfigService;

	@Resource
	private IUserService userService;
	
	

	@Override
	protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		String userAgent = req.getHeader("user-agent");
		
		if(StringUtils.isBlank(userAgent)||!userAgent.toLowerCase().contains("micromessenger")){//不是微信客户端
			chain.doFilter(request, response);
			return;
		}
		
		
		
		
		String siteId=InputSafeUtils.substringByURI(req.getRequestURI(), "/s_");
		if(StringUtils.isBlank(siteId)){
			return;
		}
		
		
		String openId = "";
		try {
			
			HttpSession session = req.getSession();
			openId = (String) session.getAttribute("openId");
			if (StringUtils.isNotBlank(openId)) {
				
				chain.doFilter(request, response);
				return;
			}
			
			IWxMpConfig wxMpConfig = wxMpConfigService.findWxMpConfigById(siteId);
			
			if(wxMpConfig==null){
				chain.doFilter(request, response);
				return;
			}
			
			
			Integer oauth2 = wxMpConfig.getOauth2();
			if(oauth2==null||oauth2<1){//不开启 oauth2.0认证
				chain.doFilter(request, response);
				return;
			}
			
			//String url = SiteUtils.getRequestURL(req);
			StringBuffer url=req.getRequestURL();
			String param=req.getQueryString();
			if(StringUtils.isNotBlank(param)){
				param = StringUtils.replace(param, "&", "---");
				url=url.append("?").append(param);
			}
			
		    req.getRequestDispatcher("/mp/mpautologin/"+siteId+"/oauth2?url=" + url).forward(request, response);
		      /**-----------------------------------------------------------
             * |                    | tomcat1           |               |
             * |                    | tomcat2   ------->                |
             * |Nginx---------------> tomcat3 业务域       | 数据域           |
             * |(DMZ)               | tomcat4   <-------                |
             * |<---tomcat(访问api)   | ......            |               |
             * |                    |                   |               |
             * |--------------------|-------------------------------------
             * 背景：DMZ可访问api接口且DMZ到业务是单向的，不允许双向访问，且只有DMZ可访问外网，业务域不允许
             * 主明：只能用下面这种 方式，让客户端重定向到DMZ的tomcat而不能用上面的在tomcat业务域服务器端转发了。
             *      DMZ的tomcat访问api接口（当然，NG要配置下，要访问API的走DMZ的tomcat，其它走业务域集群）
             */
			//rep.sendRedirect(SiteUtils.getSiteURLPath(req)+"/mp/mpautologin/"+siteId+"/oauth2?url="+ url);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}
}
