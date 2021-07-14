package com.jun.plugin.tool.web;

import javax.servlet.http.HttpServletResponse;

/** 
 * @author Wujun
 * @since   2013年9月9日 下午3:30:27 
 */
public class ResponseUtils {
	
	public static void closeCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
	}
}
