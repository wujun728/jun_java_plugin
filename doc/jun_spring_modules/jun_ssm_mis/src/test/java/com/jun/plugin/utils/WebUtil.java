package com.jun.plugin.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.springframework.util.Assert;

/**
 * 
 * @author Administrator
 *
 */
public class WebUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");
	/**
	 * 生成UUID
	 * @return
	 */
	public static String uuid(){
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		return uuid;
	}
	/**
	 * 生成订单编号
	 */
	public static String getNo(Integer hashCode){
		Date date = new Date();
		String string  = sdf.format(date);
		return hashCode+string;
	}
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	public static String getDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	

	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}
	
	
	

	public static String getArea(String strip){
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;
		String strRtn = null;
		try{
//			MyJdbc myjdbc = new MyJdbc();
//			conn = myjdbc.getConn();
			sql = "select * from fullip where startip<='" + strip + "' and endip>='" + strip + "'";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				strRtn = rs.getString("country");
			}else{
				strRtn = "δȷ��";
			}
			rs.close();
			rs = null;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (pstmt != null)
				try{
					pstmt.close();
					pstmt = null;
				}catch(Exception e){}
			if (conn != null)
				try{
					conn.close();
					conn = null;
				}catch(Exception e){}
		}
		return strRtn;
	}
	/**
	 * ��ip��ַ��ʽ��Ϊ��000.000.000.000
	 * @param ip
	 * @return ���ع��ip
	 */
	public static String strfullip(String ip){
		StringBuffer buff = new StringBuffer();
		buff.append("");
		String strzero = "000";
		int ilen = 0;
		if(ip != null){
			String[] arrip = ip.split("\\.");
			if(arrip.length == 4){
				for(int i = 0; i < 4; i++){
					if (i==0){
						ilen = arrip[i].length();
						if(ilen < 3){
							buff.append(strzero.substring(0,3-ilen)).append(arrip[i]);
						}else{
							buff.append(arrip[i]);
						}
					}else{
						ilen = arrip[i].length();
						if(ilen < 3){
							buff.append(".").append(strzero.substring(0,3-ilen)).append(arrip[i]);
						}else{
							buff.append(".").append(arrip[i]);
						}
					}
				}
			}
		}
		return buff.toString();
	}
	/**
	 * @param args
	 */
	public   void main() {
		String strip = "202.108.33.32";
		System.out.println(WebUtil.strfullip(strip));
		System.out.println(System.currentTimeMillis());
		System.out.println("ip" + strip + "���ڵ���" + WebUtil.getArea(WebUtil.strfullip(strip)));
		System.out.println(System.currentTimeMillis());
	}

	
	
	/**
	 * ɾ���ﳵ
	 */
	public static void deleteBuyCart(HttpServletRequest request){
		request.getSession().removeAttribute("buyCart");
	}
    /***
     * ��ȡURI��·��,��·��Ϊhttp://www.babasport.com/action/post.htm?method=add, �õ���ֵΪ"/action/post.htm"
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request){     
        return request.getRequestURI();
    }
    /**
     * ��ȡ��������·��(������·�����������)
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request){     
        return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
    }
    /**
     * ���cookie
     * @param response
     * @param name cookie�����
     * @param value cookie��ֵ
     * @param maxAge cookie��ŵ�ʱ��(����Ϊ��λ,����������,��3*24*60*60; ���ֵΪ0,cookie����������رն����)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {        
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge>0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    
    /**
     * ��ȡcookie��ֵ
     * @param request
     * @param name cookie�����
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
    	Map<String, Cookie> cookieMap = WebUtil.readCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie.getValue();
        }else{
            return null;
        }
    }
    
    protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }
    /**
     * ȥ��html����
     * @param inputString
     * @return
     */
    public static String HtmltoText(String inputString) {
        String htmlStr = inputString; //��html��ǩ���ַ�
        String textStr ="";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;          
        java.util.regex.Pattern p_ba;
        java.util.regex.Matcher m_ba;
        
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //����HTML��ǩ��������ʽ
            String patternStr = "\\s+";
            
            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //����script��ǩ

            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //����style��ǩ
         
            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //����html��ǩ
            
            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); //���˿ո�
         
         textStr = htmlStr;
         
        }catch(Exception e) {
                    System.err.println("Html2Text: " + e.getMessage());
        }          
        return textStr;//�����ı��ַ�
     }
    
    public static void main(String[] args) throws Exception {
		WebUtil p = new WebUtil();
		System.out.println("Web Class Path = " + p.getWebClassesPath());
		System.out.println("WEB-INF Path = " + p.getWebInfPath());
		System.out.println("WebRoot Path = " + p.getWebRoot());
	}

	public String getWebClassesPath() {
		String path = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		return path;

	}

	public String getWebInfPath() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	public String getWebRoot() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}
	

    public static String getDateStr(String pt) {
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.format(new Date());
    }
    
    public static String getDateStr(Object dateObj, String pt) {
        Date date = null;
        if (dateObj instanceof Date) {
            if (dateObj == null) {
                return "";
            }
            date = (Date) dateObj;
        } else {
            if (dateObj == null || dateObj.toString().length() == 0) {
                return "";
            }
            
            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(dateObj
                    .toString());
            date = new Date(sqlDate.getTime());
        }
        
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.format(date);
    }
    
    public static Date getDate(String str, String pt) throws ParseException {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.parse(str);
    }
    
    public static java.sql.Timestamp getSqlDate() {
        long dateTime = new Date().getTime();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(dateTime);
        return sqlDate;
    }
    
    public static java.sql.Timestamp getSqlDate(String timeValue) {
        java.sql.Timestamp sqlDate = null;
        Long dateTime = WebUtil.getDateTime(timeValue);
        if (dateTime != null) {
            sqlDate = new java.sql.Timestamp(dateTime.longValue());
        }
        return sqlDate;
    }
    
    public static Long getDateTime(String timeValue) {
        Long dateTime = null;
        
        if (timeValue != null && timeValue.trim().length() > 0) {
            timeValue = rightPadTo(timeValue, "1900-01-01 00:00:00");
            timeValue = timeValue.replace("/", "-");
            
            try {
                Date date = WebUtil
                        .getDate(timeValue, "yyyy-MM-dd HH:mm:ss");
                dateTime = new Long(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        return dateTime;
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parmsMap = new HashMap<String, String>();
        
        Map<String, String[]> properties = request.getParameterMap();
        Object obj = "";
        String value = "";
        String[] values = null;
        
        for (String key : properties.keySet()) {
            obj = properties.get(key);
            if (null == obj) {
                value = "";
            } else if (obj instanceof String[]) {
                value = "";
                values = (String[]) obj;
                for (int i = 0; i < values.length; i++) {
                    value += "," + values[i];
                }
                value = value.length() > 0 ? value.substring(1) : value;
            } else {
                value = obj.toString();
            }
            
            parmsMap.put(key, value);
        }
        
        return parmsMap;
    }
    
    
	public static String changeUTF(String str) {
		
		String newStr = null;
		try {
			newStr = new String(str.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
    
    
    public static String rightPadTo(String src, String dec) {
        String retStr = src;
        int len = src.length();
        if (dec.length() - len > 0) {
            retStr += dec.substring(len);
        }
        return retStr;
    }
    /***********************************************************************************************/
    /***********************************************************************************************/

	public static <T> T requestToBean(HttpServletRequest request, Class<T> beanClass) {
		try {
			T bean = beanClass.newInstance();
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {// 测试此枚举是否包含更多的元素。
				String name = (String) e.nextElement();// 返回此枚举的下一个元素。
				String value = request.getParameter(name);// 获取request中对应名称的值；
				// 将指定的名称和值存进指定的javaBean中对应的属性；
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 重写Converter接囗的convert方法来自定义转换器；
					public Object convert(Class type, Object value) {
						if (value == null)
							return null;
						String str = (String) value;
						if (str.trim().equals("")) {
							return null;
						}
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
						try {
							return df.parse(str); // 将字符串转为Date对象；
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}
					}
				}, Date.class);
		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean4(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
		    public Object convert(Class type, Object value) {
			if (value == null) return null;
			if (!(value instanceof String)) {
//			    throw new ConversionException("这里只支持String类型！");
			}
			if (((String) value).trim().equals("")) {// trim:返回字符串的副本，忽略前导空白和尾部空白。
			    return null;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");// 设置日期格式
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			try {
			    return df.parse((String) value); // 将字符串转为Date对象；
			} catch (ParseException e) {
			    throw new RuntimeException(e);
			}
		    }
		}, java.util.Date.class);
//		ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);
		
		
		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean3(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			@SuppressWarnings("rawtypes")
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				}else{
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
//		ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);
//		BeanUtils.populate(b, map);
		
		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	// 复制所有属性值从originBean到destinationBean中；
	public static void paramToBean(HttpServletRequest request, Object dest) {
		Object src=getAllParameters(request);
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			@SuppressWarnings("rawtypes")
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				}else{
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
//		ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);
//		BeanUtils.populate(b, map);
		
		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	// 复制所有属性值从originBean到destinationBean中；
	@SuppressWarnings("rawtypes")
	public static void MapToBean(Map map, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				}else{
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
//		ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);
		
		try {
			BeanUtils.populate(dest, map);
 		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 生成全球唯一的ID；
	public static String gnerateID() {
		return UUID.randomUUID().toString();// UUID表示通知唯一标识码
	}
	
	
	
	

	protected static Object convertToDate(Class type, Object value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (value instanceof String) {
			try {
				if (StringUtil.isEmpty(value.toString())) {
					return null;
				}
				java.util.Date date = sdf.parse(String.valueOf(value));
				if (type.equals(Timestamp.class)) {
					return new Timestamp(date.getTime());
				}
				return date;
			} catch (Exception pe) {
				return null;
			}
		} else if (value instanceof Date) {
			return value;
		}
		throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
	}

	protected static Object convertToString(Class type, Object value) {
		if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (value instanceof Timestamp) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			try {
				return sdf.format(value);
			} catch (Exception e) {
				throw new ConversionException("日期转换为字符串时出错！");
			}
		} else {
			return value.toString();
		}
	}
	//************************************************************************************
	//************************************************************************************

	private static String webAppRoot = null;

	public static String getWebAppRoot() {
		return webAppRoot;
	}

	public static void setWebAppRoot(String pWebAppRoot) {
		Assert.notNull(pWebAppRoot, "webAppRoot must not be null");
		webAppRoot = pWebAppRoot;
	}
	//************************************************************************************
	//************************************************************************************
	public static String removeHightlight(String content) {
		if (StringUtil.isEmpty(content))
			return content;
		int before = content.indexOf('<');
		int behind = content.indexOf('>');
		if (before != -1 || behind != -1) {
			behind += 1;
			content = content.substring(0, before).trim() + content.substring(behind, content.length()).trim();
			content = removeHightlight(content);
		}
		return content;
	}

 
	//************************************************************************************
	//************************************************************************************
	private static String numberFilePath;//number.txt的真实路径
	static{
		URL url = WebUtil.class.getClassLoader().getResource("config.properties");
		numberFilePath = url.getPath();//不要把Tomcat装在有空格或中文的目录下
	}
	/**
	 * 把请求参数封装到JavaBean中，前提表单的字段名和JavaBean属性保持一致
	 * @param request
	 * @param clazz 目标类型
	 * @return
	 */
	public static <T> T fillBean(HttpServletRequest request,Class<T> clazz){
		try {
			T bean = clazz.newInstance();
			//注册日期类型转换器:
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.populate(bean, request.getParameterMap());
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//生成一个唯一的编号:yyyyMMdd00000001   20150210 00000001
	public synchronized static String genApplyNumber(){
		
		try {
			//读取number.txt文件，获得当前的编号
			InputStream in = new FileInputStream(numberFilePath);
			byte data[] = new byte[in.available()];
			in.read(data);
			in.close();
			String count = new String(data);//1
			//按照约定组织成申请编号，返回
			//----------------------
			Date now = new Date();
			String prefix = new SimpleDateFormat("yyyyMMdd").format(now);//20150210
			//---------------------
			StringBuffer sb = new StringBuffer(prefix);
			for(int i=0;i<(8-count.length());i++){
				sb.append("0");
			}
			sb.append(count);
			//加1后，写入number.txt文件
			OutputStream out = new FileOutputStream(numberFilePath);
			out.write((Integer.parseInt(count)+1+"").getBytes());
			out.close();
			
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void main123(String[] args) {
		for(int i=0;i<10;i++){
			System.out.println(genApplyNumber());
		}
	}
	//************************************************************************************//
	//************************************************************************************//
    /***********************************************************************************************/
    /***********************************************************************************************/

	@Deprecated
	public static <T> T copyToBean_old(HttpServletRequest request, Class<T> clazz) {
		try {
			// ��������
			T t = clazz.newInstance();
			
			// ��ȡ���еı?Ԫ�ص����
			Enumeration<String> enums = request.getParameterNames();
			// ����
			while (enums.hasMoreElements()) {
				// ��ȡ�?Ԫ�ص����:<input type="password" name="pwd"/>
				String name = enums.nextElement();  // pwd
				// ��ȡ��ƶ�Ӧ��ֵ
				String value = request.getParameter(name);
				// ��ָ��������ƶ�Ӧ��ֵ���п���
				BeanUtils.copyProperty(t, name, value);
			}
			
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ����������ݵķ�װ
	 */
	public static <T> T copyToBean(HttpServletRequest request, Class<T> clazz) {
		try {
			// ��ע����������ת������
			// ��������
			T t = clazz.newInstance();
			BeanUtils.populate(t, request.getParameterMap());
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/***********************************************************************************************/
	/***********************************************************************************************/

	public static <T> T requestToBean22(HttpServletRequest request, Class<T> beanClass) {
		try {
			T bean = beanClass.newInstance();
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {// 测试此枚举是否包含更多的元素。
				String name = (String) e.nextElement();// 返回此枚举的下一个元素。
				String value = request.getParameter(name);// 获取request中对应名称的值；
				// 将指定的名称和值存进指定的javaBean中对应的属性；
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean22(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 重写Converter接囗的convert方法来自定义转换器；
					public Object convert(Class type, Object value) {
						if (value == null)
							return null;
						String str = (String) value;
						if (str.trim().equals("")) {
							return null;
						}
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
						try {
							return df.parse(str); // 将字符串转为Date对象；
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}
					}
				}, Date.class);
		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 生成全球唯一的ID；
	public static String gnerateID22() {
		return UUID.randomUUID().toString();// UUID表示通知唯一标识码
	}
	/***********************************************************************************************/
	/***********************************************************************************************/
	

	/**
	 * 获取当前操作系统名称. 
	 * return 操作系统名称 例如:windows,Linux,Unix等.
	 */
	public static String getOSName()
	{
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取Unix网卡的mac地址.
	 * @return mac地址
	 */
	public static String getUnixMACAddress()
	{
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try
		{
			/**
			 * Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null)
			{
				/**
				 * 寻找标示字符串[hwaddr]
				 */
				index = line.toLowerCase().indexOf("hwaddr");
				/**
				 * 找到了
				 */
				if (index != -1)
				{
					/**
					 * 取出mac地址并去除2边空格
					 */
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取Linux网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getLinuxMACAddress()
	{
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try
		{
			/**
			 * linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null)
			{
				index = line.toLowerCase().indexOf("硬件地址");
				/**
				 * 找到了
				 */
				if (index != -1)
				{
					/**
					 * 取出mac地址并去除2边空格
					 */
					mac = line.substring(index + 4).trim();
					break;
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress()
	{
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try
		{
			/**
			 * windows下的命令，显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ipconfig /all");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null)
			{
				/**
				 * 寻找标示字符串[physical address]
				 */
				index = line.toLowerCase().indexOf("physical address");
				if (index != -1)
				{
					index = line.indexOf(":");
					if (index != -1)
					{
						/**
						 * 取出mac地址并去除2边空格
						 */
						mac = line.substring(index + 1).trim();
					}
					break;
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	
	//WindowsCmd ="cmd.exe /c echo %NUMBER_OF_PROCESSORS%";//windows的特殊
	//SolarisCmd = {"/bin/sh", "-c", "/usr/sbin/psrinfo | wc -l"};  
	//AIXCmd = {"/bin/sh", "-c", "/usr/sbin/lsdev -Cc processor | wc -l"};  
	//HPUXCmd = {"/bin/sh", "-c", "echo \"map\" | /usr/sbin/cstm | grep CPU | wc -l "}; 
	//LinuxCmd = {"/bin/sh", "-c", "cat /proc/cpuinfo | grep ^process | wc -l"}; 

	/**
	 * 测试用的main方法.
	 * 
	 * @param argc
	 *            运行参数.
	 */
	public static void main1(String[] argc )
	{
		String os = getOSName();
		System.out.println(os);
		if (os.startsWith("windows"))
		{
			String mac = getWindowsMACAddress();
			System.out.println("本地是windows:" + mac);
		} else if (os.startsWith("linux"))
		{
			String mac = getLinuxMACAddress();
			System.out.println("本地是Linux系统,MAC地址是:" + mac);
		} else
		{
			String mac = getUnixMACAddress();
			System.out.println("本地是Unix系统 MAC地址是:" + mac);
		}
	}

	
	/***********************************************************************************************/
	/***********************************************************************************************/


	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr1(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:") != -1) {
			ip = "本地";
		}
		return ip;
	}
	
	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr2(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}
	
	/***********************************************************************************************/
	/***********************************************************************************************/
	// 根据名称查找指定的cookie
	public static Cookie findCookieByName(Cookie[] cs, String name) {
		if (cs == null || cs.length == 0) {
			return null;
		}

		for (Cookie c : cs) {
			if (c.getName().equals(name)) {
				return c;
			}
		}

		return null;
	}
	/***********************************************************************************************/
	/***********************************************************************************************/
    /***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/



	public static final String URL_FORM_ENCODED = "application/x-www-form-urlencoded";
	public static final String PUT = "PUT";
	public static final String POST = "POST";

	
	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length());// 去掉项目路径
		return requestPath;
	}

	public static Map getParams(HttpServletRequest request)
	{
		Map params = new HashMap();
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0)
		{
			String pairs[] = Pattern.compile("&").split(queryString);
			for (int i = 0; i < pairs.length; i++)
			{
				String p = pairs[i];
				int idx = p.indexOf('=');
				params.put(p.substring(0, idx), URLDecoder.decode(p.substring(idx + 1)));
			}

		}
		return params;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getAllParameters(HttpServletRequest request)
	{
		Map bufferMap = Collections.synchronizedMap(new HashMap());
		try
		{
			for (Enumeration em = request.getParameterNames(); em.hasMoreElements();)
			{
				String name = (String)(String)em.nextElement();
				String values[] = request.getParameterValues(name);
				String temp[] = new String[values.length];
				if (values.length > 1)
				{
					for (int i = 0; i < values.length; i++)
						temp[i] = values[i];

					bufferMap.put(name, temp);
				} else
				{
					bufferMap.put(name, values[0]);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return bufferMap;
	}

	public static String CharSetConvert(String s, String charSetName, String defaultCharSetName)
	{
		String newString = null;
		try
		{
			newString = new String(s.getBytes(charSetName), defaultCharSetName);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException nulle)
		{
			nulle.printStackTrace();
		}
		return newString;
	}
	  //******************************************************************
	//******************************************************************
	  
	//******************************************************************
	//******************************************************************
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/


	//-- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	//-- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	//-- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 设置客户端缓存过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		//Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		//Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			//中文文件名支持
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果Parameter名已去除前缀.
	 */
	@SuppressWarnings("unchecked")
	public static Map getParametersStartingWith(HttpServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {//NOSONAR
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
    
}
