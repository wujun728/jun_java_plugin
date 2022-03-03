package org.springrain.frame.shiro;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springrain.frame.util.CookieUtils;
import org.springrain.frame.util.FileUtils;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.InputSafeUtils;
import org.springrain.system.service.IStaticHtmlService;
/**
 * 页面静态化的过滤器
 * @author caomei
 *
 */


@Component("statichtml")
public class FrameStaticHtmlFilter extends OncePerRequestFilter {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IStaticHtmlService staticHtmlService;
	
	

	@Override
    protected void doFilterInternal(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
        HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		int i=uri.indexOf(contextPath);
		if(i>-1){
			uri=uri.substring(i+contextPath.length());
		}
		
		
		 String siteId=InputSafeUtils.substringByURI(req.getRequestURI(), "/s_");
		 
		 
		 if(StringUtils.isBlank(siteId)){//URL中没有siteId,从cookie中取值
			 siteId = CookieUtils.getCookieValue(req, GlobalStatic.springraindefaultSiteId);
		 }
		
		//cache key,可以根据URI从数据库进行查询资源Id
		String htmlPath=null;
		try {
			htmlPath = staticHtmlService.findHtmlPathByURI(siteId,uri);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		if(StringUtils.isBlank(htmlPath)||"error".equals(htmlPath)){//缓存中不存在
			chain.doFilter(request, response);
			return;
		}
		
		File htmlFile = new File(GlobalStatic.staticHtmlDir+htmlPath);  
		if(!htmlFile.exists()){
			chain.doFilter(request, response);
			return;
		}
		
		    response.setContentType("text/html;charset="+GlobalStatic.defaultCharset);
		    response.setCharacterEncoding(GlobalStatic.defaultCharset);

            PrintWriter writer = response.getWriter();
            FileUtils.readIOFromFile(writer, htmlFile);
          
		}

}
