package com.ketayao.utils.web;

import javax.servlet.http.HttpServletResponse;

/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年9月9日 下午3:30:27 
 */
public class ResponseUtils {
	
	public static void closeCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
	}
}
