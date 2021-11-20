package com.jun.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebFilter(filterName = "LoginFilter", urlPatterns = { "/page/test" }, initParams = {
		@WebInitParam(name = "name", value = "xiazdong"),
		@WebInitParam(name = "age", value = "20") }, asyncSupported = true)
public class LoginFilter implements Filter {

	private Logger log = Logger.getLogger(LoginFilter.class);
	private FilterConfig conf;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.conf = filterConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		/*******************************************************************************************/
		/*******************************************************************************************/
		/*******************************************************************************************/
		// 在这儿读取cookie
		// 获取用户请求的uri
		String uri = request.getRequestURI();// 就是/LoginServlet
		if (request.getSession().getAttribute("exit") == null) {
			if (request.getSession().getAttribute("name") == null) {
				if (!uri.contains("/LoginServlet")) {
					// 获取所的有cookie
					Cookie[] cs = request.getCookies();
					if (cs != null) {
						for (Cookie c : cs) {
							if (c.getName().equals("autoLogin")) {// 如果存在自动登录的cookie
								String value = c.getValue();// 用户名称
								value = URLDecoder.decode(value, "UTF-8");
								request.getSession().setAttribute("name", value);
								break;
							}
						}
					}
				}
			}
		} else {
			request.getSession().removeAttribute("exit");
		}

		/*******************************************************************************************/
		/*******************************************************************************************/
		/*******************************************************************************************/
		if (request.getSession().getAttribute("user") == null) {
			System.err.println("用户还没有登录");
			request.getSession().setAttribute("msg", "你还没有登录，请先登录");
			response.sendRedirect(request.getContextPath() + "/index.jsp?error=2");
		} else {
			chain.doFilter(request, response);
		}
		/*******************************************************************************************/
		/*******************************************************************************************/
		/*******************************************************************************************/

		/*******************************************************************************************/
		/*******************************************************************************************/
		/*******************************************************************************************/
		// String uri = request.getRequestURI(); // /emp_sys/login.jsp
		String requestPath = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
		if ("admin".equals(requestPath) || "login1.jsp".equals(requestPath)) {
			chain.doFilter(request, response);
		} else {
			HttpSession session = request.getSession(false);
			if (session != null) {
				Object obj = session.getAttribute("loginInfo");
				if (obj != null) {
					chain.doFilter(request, response);
					return;
				} else {
					uri = "/login.jsp";
				}

			} else {
				uri = "/login.jsp";
			}
			request.getRequestDispatcher(uri).forward(request, response);
		}
		/*******************************************************************************************/
		/*******************************************************************************************/
		/*******************************************************************************************/

		/*******************************************************************************************/
		/*******************************************************************************************/
		/*******************************************************************************************/
		if (request.getSession().getAttribute("user") == null) {
			String loginPath = conf.getInitParameter("loginPage");
			if (request.getSession().getAttribute("currentBook") == null) {
				// 解析url
				// String uri = request.getRequestURI();
				// 解析参数
				String param = request.getQueryString();
				log.info(uri + "?" + param);
				String bookId = request.getParameter("id");
				request.getSession().setAttribute("currentBook", bookId);
			}
			req.getRequestDispatcher(loginPath).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
		/*******************************************************************************************/
		/*******************************************************************************************/
		/*******************************************************************************************/
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
