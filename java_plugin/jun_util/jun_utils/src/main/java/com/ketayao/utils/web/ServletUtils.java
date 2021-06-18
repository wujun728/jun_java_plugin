package com.ketayao.utils.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {
	/**
	 * 设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		final String CONTENT_DISPOSITION = "Content-Disposition";
		
		try {
			String agent = request.getHeader("User-Agent");
			String encodedfileName = null;
	        if (null != agent) {  
	        	agent = agent.toLowerCase();  
	            if (agent.contains("firefox") || agent.contains("chrome") || agent.contains("safari")) {  
	    			encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
	            } else if (agent.contains("msie")) {  
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            } else if (agent.contains("opera")) {  
	            	encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
	            } else {
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            }
	        }
			
	        response.setHeader(CONTENT_DISPOSITION, "attachment; " + encodedfileName);
		} catch (UnsupportedEncodingException e) {
		}
	}
}