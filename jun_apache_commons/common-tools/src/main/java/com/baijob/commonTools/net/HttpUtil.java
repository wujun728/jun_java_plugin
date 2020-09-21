package com.baijob.commonTools.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.FileUtil;
import com.baijob.commonTools.LangUtil;
import com.baijob.commonTools.RegexUtil;

/**
 * Http请求工具类
 * @author luxiaolei@baijob.com
 */
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public final static String HTTP_CHARSET = "GBK";
	/** 未知的标识 */
	public final static String UNKNOW = "unknown";
	
	private static Map<String, String> cookies;
	
	public HttpUtil() {
		cookies = new HashMap<String, String>();
	}
	
	/**
	 * 发送get请求
	 * @param urlString 网址
	 * @param customCharset 自定义请求字符集
	 * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
	 * @throws IOException
	 */
	public String get(String urlString, String customCharset) throws IOException{
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		String host = url.getHost();
		String cookie = cookies.get(host);
		if(cookie != null) conn.addRequestProperty("Cookie", cookie);
		
//		conn.addRequestProperty("User-Agent", "Baiduspider+(+http://www.baidu.com/search/spider.htm)");
		conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.83 Safari/537.1");
		
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		
		String setCookie = conn.getHeaderField("Set-Cookie");
		if(setCookie != null){
			logger.debug("Set Cookie: " + setCookie);
			cookies.put(host, setCookie);
		}
		
		/* 获取内容 */
		int contentLength = conn.getContentLength();
		StringBuilder content = new StringBuilder(contentLength > 0 ? contentLength : 16);
		BufferedReader bufferedReader = null;
		String charset = getCharsetFromConn(conn);
		if(charset == null){
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}else{
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
		}
		String line = bufferedReader.readLine();
		while (line != null) {  
			content.append(line).append("\n");  
            line = bufferedReader.readLine();  
        }
        FileUtil.close(bufferedReader);
        conn.disconnect();
        
        return content.toString();
	}
	
	/**
	 * 编码字符为 application/x-www-form-urlencoded
	 * @param content	被编码内容
	 * @return 编码后的字符
	 */
	public static String encode(String content, String charset){
		if(LangUtil.isEmpty(content)) return content;
		
		String encodeContnt = null;
		try {
			encodeContnt = URLEncoder.encode(content, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unsupported encoding: {}", charset);
		}
		 return encodeContnt;
	}
	
	/**
	 * 解码application/x-www-form-urlencoded字符
	 * @param content	被编码内容
	 * @return 编码后的字符
	 */
	public static String decode(String content, String charset){
		if(LangUtil.isEmpty(content)) return content;
		
		String encodeContnt = null;
		try {
			encodeContnt = URLDecoder.decode(content, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unsupported encoding: {}", charset);
		}
		return encodeContnt;
	}
	
	/**
	 * 格式化URL链接
	 * @param url 需要格式化的URL
	 * @return 格式化后的URL，如果提供了null或者空串，返回null
	 */
	public static String formatUrl(String url){
		if(LangUtil.isEmpty(url)) return null;
		if(url.startsWith("http://") || url.startsWith("https://")) return url;
		return "http://" + url;
	}
	
	/**
	 * 格式化Http Header 的name部分
	 * @param headerName 头名称
	 * @return 格式化后的 header name, 如果headerName为空，返回null
	 */
	public static String formatHeaderName(String headerName) {
		if(LangUtil.isEmpty(headerName)) {
			return null;
		}else {
			headerName = headerName.toLowerCase();
		}
		
		if (headerName.equals("etag")) {
			return "ETag";
		}

		if (headerName.equals("www-authenticate")) {
			return "WWW-Authenticate";
		}

		char[] name = headerName.toCharArray();

		boolean capitalize = true;

		for (int i = 0; i < name.length; i++) {
			char c = name[i];

			if (c == '-') {
				capitalize = true;
				continue;
			}

			if (capitalize) {
				name[i] = Character.toUpperCase(c);
				capitalize = false;
			} else {
				name[i] = Character.toLowerCase(c);
			}
		}

		return new String(name);
	}
	
	/**
	 * 获取客户端IP
	 * @param request 请求对象
	 * @return IP地址
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (isUnknow(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isUnknow(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isUnknow(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (isUnknow(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多级反向代理检测
		if (ip != null && ip.indexOf(",") > 0) {
			ip = ip.trim().split(",")[0];
		}
		return ip;
	}
	
	/**
	 * 从Http连接的头信息中获得字符集
	 * @param conn HTTP连接对象
	 * @return 字符集
	 */
	private static String getCharsetFromConn(HttpURLConnection conn){
		String charset = conn.getContentEncoding();
		if(charset == null || "".equals(charset.trim())){
			String contentType = conn.getContentType();
			charset = RegexUtil.get("charset=(.*)", contentType, 1);
		}
		return charset;
	}
	
	/**
	 * 检测给定字符串是否为未知，多用于检测HTTP请求相关<br/>
	 * 
	 * @param checkString 被检测的字符串
	 * @return 是否未知
	 */
	private static boolean isUnknow(String checkString) {
		return LangUtil.isEmpty(checkString) || UNKNOW.equalsIgnoreCase(checkString);
	}
}
