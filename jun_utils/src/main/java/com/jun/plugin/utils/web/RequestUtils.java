package com.jun.plugin.utils.web;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 用于处理HTTP请求的工具类
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年7月26日 下午3:27:29
 */
public class RequestUtils {

	/**
	 * 获取客户端IP地址，此方法用在proxy环境中
	 * @param req
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if(StringUtils.isNotBlank(ip)){
			String[] ips = StringUtils.split(ip,',');
			if(ips!=null){
				for(String tmpip : ips){
					if(StringUtils.isBlank(tmpip))
						continue;
					tmpip = tmpip.trim();
					if(isIPAddr(tmpip) && !tmpip.startsWith("10.") && !tmpip.startsWith("192.168.") 
							&& !"127.0.0.1".equals(tmpip)){
						return tmpip.trim();
					}
				}
			}
		}
		ip = req.getHeader("x-real-ip");
		if(isIPAddr(ip))
			return ip;
		ip = req.getRemoteAddr();
		if(ip.indexOf('.')==-1)
			ip = "127.0.0.1";
		return ip;
	}

	/**
	 * 判断是否为搜索引擎
	 * @param req
	 * @return
	 */
	public static boolean isRobot(HttpServletRequest req){
		String ua = req.getHeader("user-agent");
		if(StringUtils.isBlank(ua)) return false;
		return (ua != null
				&& (ua.indexOf("Baiduspider") != -1 || ua.indexOf("Googlebot") != -1
						|| ua.indexOf("sogou") != -1
						|| ua.indexOf("sina") != -1
						|| ua.indexOf("iaskspider") != -1
						|| ua.indexOf("ia_archiver") != -1
						|| ua.indexOf("Sosospider") != -1
						|| ua.indexOf("YoudaoBot") != -1
						|| ua.indexOf("yahoo") != -1 
						|| ua.indexOf("yodao") != -1
						|| ua.indexOf("MSNBot") != -1
						|| ua.indexOf("spider") != -1
						|| ua.indexOf("Twiceler") != -1
						|| ua.indexOf("Sosoimagespider") != -1
						|| ua.indexOf("naver.com/robots") != -1
						|| ua.indexOf("Nutch") != -1
						|| ua.indexOf("spider") != -1));	
	}

	/**
	 * 获取COOKIE
	 * 
	 * @param name
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null)	return null;
		for (Cookie ck : cookies) {
			if (StringUtils.equalsIgnoreCase(name,ck.getName())) 
				return ck;			
		}
		return null;
	}

	/**
	 * 获取COOKIE
	 * 
	 * @param name
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null)	return null;
		for (Cookie ck : cookies) {
			if (StringUtils.equalsIgnoreCase(name,ck.getName())) 
				return ck.getValue();			
		}
		return null;
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name,
			String value, int maxAge) {
		setCookie(request,response,name,value,maxAge,true);
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name,
			String value, int maxAge, boolean all_sub_domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		if(all_sub_domain){
			String serverName = request.getServerName();
			String domain = getDomainOfServerName(serverName);
			if(domain!=null && domain.indexOf('.')!=-1){
				cookie.setDomain('.' + domain);
			}
		}
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static void deleteCookie(HttpServletRequest request,
			HttpServletResponse response, String name, boolean all_sub_domain) {
		setCookie(request,response,name,"",0,all_sub_domain);
	}

	/**
	 * 获取用户访问URL中的根域名
	 * 例如: www.dlog.cn -> dlog.cn
	 * @param req
	 * @return
	 */
	public static String getDomainOfServerName(String host){
		if(isIPAddr(host))
			return null;
		String[] names = StringUtils.split(host, '.');
		int len = names.length;
		if(len==1) return null;
		if(len==3){
			return makeup(names[len-2],names[len-1]);
		}
		if(len>3){
			String dp = names[len-2];
			if(dp.equalsIgnoreCase("com")||dp.equalsIgnoreCase("gov")||dp.equalsIgnoreCase("net")||dp.equalsIgnoreCase("edu")||dp.equalsIgnoreCase("org"))
				return makeup(names[len-3],names[len-2],names[len-1]);
			else
				return makeup(names[len-2],names[len-1]);
		}
		return host;
	}

	/**
	 * 判断字符串是否是一个IP地址
	 * @param addr
	 * @return
	 */
	public static boolean isIPAddr(String addr){
		if(StringUtils.isEmpty(addr))
			return false;
		String[] ips = StringUtils.split(addr, '.');
		if(ips.length != 4)
			return false;
		try{
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
					&& ipc <= 255 && ipd >= 0 && ipd <= 255;
		}catch(Exception e){}
		return false;
	}
	
	private static String makeup(String...ps){
		StringBuilder s = new StringBuilder();
		for(int idx = 0; idx < ps.length; idx++){
			if(idx > 0)
				s.append('.');
			s.append(ps[idx]);
		}
		return s.toString();
	}

	/**
	 * 获取HTTP端口
	 * @param req
	 * @return
	 * @throws MalformedURLException
	 */
	public static int getHttpPort(HttpServletRequest req) {
		try {
			return new URL(req.getRequestURL().toString()).getPort();
		} catch (MalformedURLException excp) {
			return 80;
		}
	}	

	/**
	 * 获取浏览器提交的整形参数
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public static int getParam(HttpServletRequest req, String param, int defaultValue){
		return NumberUtils.toInt(req.getParameter(param), defaultValue);
	}
	/**
	 * 获取浏览器提交的整形参数
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public static long getParam(HttpServletRequest req, String param, long defaultValue){
		return NumberUtils.toLong(req.getParameter(param), defaultValue);
	}
	
	/**
	 * 获取浏览器提交的字符串参数?
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public static String getParam(HttpServletRequest req, String param, String defaultValue){
		String value = req.getParameter(param);
		return (StringUtils.isEmpty(value))?defaultValue:value;
	}
}
