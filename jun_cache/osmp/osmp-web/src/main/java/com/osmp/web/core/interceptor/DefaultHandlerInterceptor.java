/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.base.core.SystemGlobal;
import com.osmp.web.user.entity.User;
import com.osmp.web.utils.Utils;

public class DefaultHandlerInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			// 把项目路径设置到request中
			request.setAttribute("BASE_PATH", request.getContextPath());
			request.setAttribute("PROJECT_NAME", "OSMP-WEB");
		}
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		String contPath = request.getContextPath();
		if (null != contPath && !"".equals(contPath) && !"/".equals(contPath)) {
			requestURI = requestURI.replaceAll(contPath, "");

		}

		User u = SystemGlobal.currentLoginUser();
		// 登录请求不用连接
		if (u != null && !Utils.isEmpty(requestURI) || requestURI.endsWith("/login.do")
				|| requestURI.endsWith("xtmsg.do") || requestURI.endsWith("jvmMomitor.do")
				|| requestURI.contains("http/") || requestURI.startsWith("/cxf")) {
			return true;
		}
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return false;
	}

}
