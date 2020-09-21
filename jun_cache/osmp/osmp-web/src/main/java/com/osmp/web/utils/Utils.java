package com.osmp.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 包含常用工具方法
 *
 * @author
 * @version 1.0 2013-2-26
 */
public class Utils {

	private final static FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	/**
	 * 判断是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.trim().length() == 0;
	}

	/**
	 * base64加密
	 *
	 * @param binaryData
	 * @return <code>String</code>
	 */
	public static String base64Encode(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return (new sun.misc.BASE64Encoder()).encode(str.getBytes());
	}

	/**
	 * 数组的Join方法
	 *
	 * @param obj
	 * @param sep
	 * @return
	 */
	public static String join(Object[] obj, String sep) {
		if (obj == null) {
			return null;
		}

		final int length = obj.length;
		if (length == 0) {
			return "";
		}
		final StringBuilder sb = new StringBuilder().append(obj[0]);

		for (int i = 1; i < length; i++) {
			sb.append(sep).append(obj[i]);
		}

		return sb.toString();
	}

	/**
	 * 把字符串转换成double 如果一次返回defValue
	 *
	 * @param value
	 * @param defValue
	 * @return
	 */
	public static double stringToDouble(String value, double defValue) {

		try {
			return Double.parseDouble(value);
		} catch (final NumberFormatException e) {
			return defValue;
		}

	}

	/**
	 * 字符串转换为long
	 *
	 * @param val
	 * @param defVal
	 *            如果转换失败给定的默认值
	 * @return
	 */
	public static long parseLong(String val, long defVal) {
		try {
			return Long.parseLong(val);
		} catch (final Exception e) {
			return defVal;
		}
	}

	/**
	 * 将字符串转换成日期格式
	 *
	 * @param pattern
	 * @param date
	 * @return 转换失败时返回null
	 */
	public static Date stringToDate(String pattern, String date) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (final Exception e) {
			return null;
		}
	}

	/*****
	 * 将毫秒转换成日期格式的字符串
	 *
	 * @param millis
	 * @return
	 */
	public static String longTodateString(long millis) {
		return FAST_DATE_FORMAT.format(millis);
	}

	/**
	 * 转换首字母大写
	 *
	 * @param str
	 * @return
	 */
	public static String firstUpper(String str) {
		final String s = (str.charAt(0) + "").toUpperCase();
		return s + str.substring(1);
	}

	/**
	 * 把时间转换成yyyy-MM-dd HH:mm:ss 格式
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		return FAST_DATE_FORMAT.format(date);
	}

	/**
	 * 把时间转换成yyyy年MM月dd日HH时mm分ss秒 格式
	 *
	 * @param date
	 * @return
	 */
	public static String dateToZhString(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(date);
	}

	/****
	 * 将时间转换成时间戳格式
	 *
	 * @param date
	 * @return
	 */
	public static String dateToTimeStamp(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		}

		return null;
	}

	/******
	 * 将日期格式2013-10-11T10:28:50.442382转换成日期对应毫秒数字符串
	 *
	 * @return
	 */
	public static String stringtodate(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}
		final String pattern = "\\d{4}-\\d{2}-\\d{2}[a-zA-Z\\s]+\\d{2}:\\d{2}:\\d{2}.\\d*";
		final StringBuffer sbf = new StringBuffer();
		if (date.matches(pattern)) {
			for (final String s : StringUtils.substringBefore(date, ".").split("[a-zA-Z\\s]+")) {
				sbf.append(s + " ");
			}
			return String.valueOf(stringToDate("yyyy-MM-dd hh:mm:ss", sbf.toString().trim()).getTime());
		} else {
			return date;
		}
	}

	/**
	 * 格式化mac地址按没两位分割
	 *
	 * @param mac
	 *            需要格式化的mac地址
	 * @param sep
	 *            分隔符 ":"或者" "等
	 * @return
	 */
	public static String formatMac(String mac, String sep) {
		if (Assert.isEmpty(mac)) {
			return "";
		}

		mac = mac.replaceAll("[^a-zA-Z0-9]", "");

		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < mac.length(); i += 2) {
			sb.append(mac.substring(i, i + 2) + sep);
		}

		return sb.substring(0, sb.length() - sep.length());

	}

	/**
	 * 返回异常的字符串
	 *
	 * @param e
	 * @return
	 */
	public static String exceptionToString(Exception e) {
		String str = "";
		if (e != null) {
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			str = sw.toString();
			pw.close();
			try {
				sw.close();
			} catch (final IOException e1) {
				e1.printStackTrace();
			}
		}

		return str;
	}

	/**
	 * 如果字符串为空则返回def否则返回str
	 *
	 * @param str
	 * @param def
	 * @return
	 */
	public static String nullToDefault(String str, String def) {
		return Assert.isEmpty(str) ? def : str;
	}

	/**
	 * 将毫秒数换算成x天x时x分x秒x毫秒
	 *
	 * @param ms
	 * @return
	 */
	public static String millisToTime(long ms) {
		final int ss = 1000;
		final int mi = ss * 60;
		final int hh = mi * 60;
		final int dd = hh * 24;

		final long day = ms / dd;
		final long hour = (ms - day * dd) / hh;
		final long minute = (ms - day * dd - hour * hh) / mi;
		final long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		final long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		final StringBuffer sb = new StringBuffer("");
		if (day != 0l) {
			sb.append(day + "天");
		}
		if (hour != 0l) {
			sb.append(hour + "小时");
		}
		if (minute != 0l) {
			sb.append(minute + "分");
		}

		if (milliSecond != 0l) {
			sb.append(second + "." + milliSecond + "秒");
		} else {
			sb.append(second + "秒");
		}
		return sb.toString();
	}

	/**
	 * 字符串trim操作
	 *
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str != null ? str.trim() : null;
	}

	/**
	 * 字符串trim操作
	 *
	 * @param str
	 * @return
	 */

	/******
	 * base64解码 返回解码后的字符串
	 *
	 * @param str
	 *            base64编码后的字符串
	 * @return
	 */
	public static String decodeBase64(String str) {
		if (str == null || str.length() == 0) {
			return "";
		} else {
			final Base64 base64 = new Base64();
			try {
				final byte[] debytes = base64.decode(str);
				return new String(debytes);
			} catch (final Exception e) {
				e.printStackTrace();
				return "";
			}
		}
	}

	public static String sqlParaFormat(String str) {
		return str != null ? str.replaceAll("'", "''") : null;
	}

	public static String covertToUTF8(String str) {
		if (str == null) {
			return str;
		}
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
		}
		return str;
	}

	public static String covertToGBK(String str) {
		if (str == null) {
			return str;
		}
		try {
			return new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (final UnsupportedEncodingException e) {
		}
		return str;
	}

	/**
	 * 使用ssh执行linux远程命令
	 *
	 * @param hostname
	 *            主机IP地址
	 * @param port
	 *            ssh访问端口号，传递null使用默认端口22
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param command
	 *            需要执行的命令，只能一行<br>
	 *            举例：sshRomoteCommand("10.34.39.112", null, "root", "123456",
	 *            "cd /home/osmp.WebServer/tomcat-8080/conf/;cat server.xml &"
	 *            )
	 * @return
	 */
	public static Map<String, Object> sshRomoteCommand(String hostname, String port, String username, String password,
			String command) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("msg", "连接服务器异常");

		final StringBuilder sb = new StringBuilder();
		// 指明连接主机的IP地址
		Connection conn;
		if (null != port) {
			Integer valueOf = 22;
			try {
				valueOf = Integer.valueOf(port);
			} catch (NumberFormatException e) {
				map.put("success", false);
				map.put("msg", "端口号只能为数字");
				return map;
			}
			conn = new Connection(hostname, valueOf);
		} else {
			conn = new Connection(hostname);
		}
		Session ssh = null;
		InputStream is = null;
		BufferedReader brs = null;
		try {
			// 连接到主机
			conn.connect();
			// 使用用户名和密码校验
			final boolean isconn = conn.authenticateWithPassword(username, password);
			if (!isconn) {
				map.put("msg", "请检查服务器IP、端口、用户名、密码是否正确");
			} else {
				ssh = conn.openSession();
				// 使用多个命令用分号隔开
				// ssh.execCommand("pwd;cd /tmp;mkdir hb;ls;ps -ef|grep weblogic");
				// cd
				// /app/weblogic/Oracle/Middleware/user_projects/domains/base_domain;./startWebLogic.sh
				// &
				ssh.execCommand(command);
				// 只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
				// ssh.execCommand("mkdir hb");

				// 将屏幕上的文字全部打印出来
				is = new StreamGobbler(ssh.getStdout());
				brs = new BufferedReader(new InputStreamReader(is));
				String line;
				while (true) {
					line = brs.readLine();
					if (line == null) {
						break;
					}
					sb.append(line).append("\r\t");
				}
				map.put("success", true);
				map.put("msg", sb);
				return map;
			}
		} catch (final IOException e) {
			map.put("msg", e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			if (brs != null) {
				try {
					brs.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			// 连接的Session和Connection对象都需要关闭
			if (ssh != null) {
				ssh.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return map;
	}

	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
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
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * JSON字符串转MAP
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = null;
		try {
			json = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static void main(String args[]) {
		final long l = 1383667200000l;
		final long l1 = 1383840000000l;
		System.out.println(longTodateString(l));
		System.out.println(longTodateString(l1));

		/*
		 * System.out.println(sshRomoteCommand("10.34.41.123", null, "root",
		 * "root",
		 * "cd /home/osmp.WebServer/tomcat-8080/bin; ./shutdown.sh &"));
		 */
		System.out.println(sshRomoteCommand("10.34.41.123", null, "root", "root",
				"source /etc/profile;cd /home/osmp.WebServer/tomcat-8080/bin;./shutdown.sh &"));
	}

	public static <T> boolean contains(final T[] array, final T v) {
		for (final T e : array)
			if (e == v || v != null && v.equals(e))
				return true;
		return false;
	}
}
