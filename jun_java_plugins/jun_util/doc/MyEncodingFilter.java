package org.myframework.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @rem: 功能说明:处理AJAX应用的中文问题；使用本过滤器，提交AJAX请求时必须使用POST方式！
 * @author: 王辉
 * @version: 1.0
 * @since 20100408
 */
public class MyEncodingFilter extends OncePerRequestFilter {
	public static class Timer {
		private long initTime;

		public long getCostTime() {
			// log.debug("end Time-------" + System.currentTimeMillis() );
			long costTime = System.currentTimeMillis() - initTime;
			// log.debug("cost Time-------" + costTime);
			return costTime;
		}

		public Timer() {
			initTime = System.currentTimeMillis();
			// log.debug("start Time-------" + initTime );
		}
	}

	public MyEncodingFilter() {
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setLogVisitHis(boolean logVisitHis) {
		this.logVisitHis = logVisitHis;
	}

	/*
	 * 拦截所有链接，1.进行编码设置；2.记录在MENU_INFO中有记录的URI
	 */
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Timer timer = new Timer();
		request.setAttribute(DEFAULT_TIMER_ATTRIBUTE, timer);

		String requestEncoding = getRequestEncoding(request, encoding);
		request.setCharacterEncoding(requestEncoding);
		response.setCharacterEncoding(encoding); // TOMCAT调试时该句不可注释掉
		filterChain.doFilter(request, response);
		if (logVisitHis) {
			logUrlVisitHis(request);
		}
	}

	/**
	 * JQUERY 的AJAX POST请求，使用UTF-8编码 ,其他情况使用默认编码
	 * 
	 * @param request
	 * @param defaultEncoding
	 * @return
	 */
	private String getRequestEncoding(HttpServletRequest request,
			String defaultEncoding) {
		String encoding = "";
		String requestedWith = request.getHeader("X-Requested-With");
		String contentType = request.getContentType();
		if ("XMLHttpRequest".equalsIgnoreCase(requestedWith)
				&& contentType != null
				&& contentType.startsWith("application/x-www-form-urlencoded")) {
			encoding = AJAX_POST_ENCODING;
		} else {
			encoding = defaultEncoding;
		}
		return encoding;
	}

	/**
	 * 
	 * 记录员工以不同职位访问URI的历史
	 * 
	 * @param request
	 */
	private void logUrlVisitHis(HttpServletRequest request) {
	}

	private static String AJAX_POST_ENCODING = "UTF-8";
	public static final String DEFAULT_TIMER_ATTRIBUTE = "ims.timer";
	private static final Log log = LogFactory.getLog(MyEncodingFilter.class);
	private String encoding;
	private boolean logVisitHis;

}
