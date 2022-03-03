package org.springrain.frame.shiro;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.IPUtils;
import org.springrain.system.entity.Fwlog;
import org.springrain.system.service.IFwlogService;
import org.springrain.system.service.IMenuService;
/**
 * 记录访问日志的过滤器
 * @author caomei
 *
 */


@Component("framefwlog")
public class FrameFWLogFilter extends OncePerRequestFilter {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private IMenuService menuService;
	
	
	@Resource
	private IFwlogService fwlogService;
	
	@Override
	protected void doFilterInternal(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String uri = req.getRequestURI();
		String requestURL = req.getRequestURL().toString();
		String contextPath = req.getContextPath();
		if(uri.endsWith("/pre")){// 去掉pre
			uri=uri.substring(0,uri.length()-4);
		}
		if(uri.endsWith("/json")){// 去掉json
			uri=uri.substring(0,uri.length()-5);
		}
		if(uri.endsWith("/more")){// 去掉more
			uri=uri.substring(0,uri.length()-5);
		}
		int i=uri.indexOf(contextPath);
		if(i>-1){
			uri=uri.substring(i+contextPath.length());
		}
		if(StringUtils.isBlank(uri)){
			uri="/";
		}
		
		
		
		
		 boolean permitted = false;
		 if("/".equals(uri)){
			 permitted=true;
		 }else{
		 permitted= SecurityUtils.getSubject().isPermitted(uri);
		 }
		 String isqx="否";
		 if(permitted){
			 isqx="是";
		 }
		 String ip = IPUtils.getClientAddress(req);
		
		 Fwlog fwlog=new Fwlog();
		 fwlog.setFwUrl(requestURL);
		 fwlog.setIsqx(isqx);
		 fwlog.setIp(ip);
	
		fwlog.setUserCode(SessionUser.getUserCode());
		fwlog.setUserName(SessionUser.getUserName());
		Date startDate=new Date();
		fwlog.setStartDate(startDate);
		fwlog.setStrDate(DateUtils.convertDate2String("yyyy-MM-dd HH:mm:ss.SSSS", startDate));
		HttpSession httpSession = req.getSession(false);
		if(httpSession!=null){
			fwlog.setSessionId(httpSession.getId());
		}
		try {
			String menuName = menuService.getNameByPageurl(uri);
			//req.setAttribute(GlobalStatic.pageurlName, menuName);
			fwlog.setMenuName(menuName);
			fwlogService.save(fwlog);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		chain.doFilter(request, response);
	}
	
	
}
