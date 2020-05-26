package org.unique.plugin.image;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.plugin.image.handler.ImageHandler;
import org.unique.plugin.image.util.ImageUtil;

/**
 * 图片处理核心过滤器
 * 
 * @author rex
 * 
 */
@WebFilter(urlPatterns = "/upload/*", asyncSupported = true)
public class ImageFilter implements Filter {

	private int contextPathLength;
	
	private ImageHandler imageHandler;
	
	/**
	 * init
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		String contextPath = config.getServletContext().getContextPath();
		contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0
				: contextPath.length());
		imageHandler = ImageHandler.single();
	}

	/**
	 * dofilter
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("qqqq");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String target = request.getRequestURI().replaceFirst(
				request.getContextPath(), "");
		if (contextPathLength != 0) {
			target = request.getRequestURI().substring(contextPathLength);
		}
		String fileName = target.substring(target.lastIndexOf("/") + 1);
		if (ImageUtil.isImg(fileName) && null != request.getQueryString()) {
			imageHandler.handler(target, request, response);
		}
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		imageHandler = null;
	}

}
