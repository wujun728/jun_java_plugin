package com.caland.common.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

/**
 * 上下文信息拦截器
 * 
 * 包括登录信息、权限信息、站点信息
 */
public class FrontContextInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 返回页面
	 */
	public static final String RETURN_URL = "returnUrl";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {


		return true;
	}

	/*
	 * @Override public void postHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object handler, ModelAndView mav) throws
	 * Exception { User user = (User) sessionProvider.getAttribute(request,
	 * Constants.USER_SESSION); if(user != null){
	 * request.setAttribute("isLogin", "true"); }else{
	 * request.setAttribute("isLogin", "false"); } }
	 */
	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl);//.append("?");
		//buff.append(RETURN_URL).append("=")
			//	.append(RequestUtils.getLocation(request));
		// if (!StringUtils.isBlank(processUrl)) {
		// buff.append("&").append(PROCESS_URL).append("=").append(
		// getProcessUrl(request));
		// }
		System.out.println(buff.toString());
		return buff.toString();
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}

	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean permistionPass(String uri, Set<String> perms,
			boolean viewOnly) {
		String u = null;
		int i;
		for (String perm : perms) {
			if (uri.startsWith(perm)) {
				// 只读管理员
				if (viewOnly) {
					// 获得最后一个 '/' 的URI地址。
					i = uri.lastIndexOf("/");
					if (i == -1) {
						throw new RuntimeException("uri must start width '/':"
								+ uri);
					}
					u = uri.substring(i + 1);
					// 操作型地址被禁止
					if (u.startsWith("o_")) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得第三个路径分隔符的位置
	 * 
	 * @param request
	 * @throws IllegalStateException
	 *             访问路径错误，没有三(四)个'/'
	 */
	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		if(null != ctxPath){
			uri = uri.substring(ctxPath.length());
		}
		return uri;
	}

	private Integer adminId;
	private String[] excludeUrls;

	private String loginUrl;
	private String processUrl;
	private String returnUrl;


	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

}