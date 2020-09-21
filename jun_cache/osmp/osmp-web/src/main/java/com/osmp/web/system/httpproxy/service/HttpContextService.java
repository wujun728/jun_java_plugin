package com.osmp.web.system.httpproxy.service;

import javax.servlet.http.HttpServletRequest;

import com.osmp.web.system.httpproxy.entity.HttpContext;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2015年4月20日 下午2:22:17
 */
public interface HttpContextService {

	/**
	 * 根据URL解析HTTP上下文
	 * 
	 * @param url
	 * @return
	 */
	public HttpContext parse(HttpServletRequest request, String url);

	/**
	 * 根据URL和规则路由到不同的服务器
	 * 
	 * @param request
	 * @param url
	 * @return 如果为"",表示请求不满足分发策略或者服务器没有运行。为"/"表示没有设置服务的url
	 */
	public String dispathByUrl(HttpServletRequest request, String url);
	
	/**
	 * 不满足分发策略时，获得任意一个运行，并且有请求服务id的“服务器访问managerUrl”
	 * 
	 * @param request
	 * @param url
	 * @return 服务访问URL(如果所有的服务器都不满足，则返回"")
	 */
	public String getOtherUrl(HttpServletRequest request, String url);

}
