package com.jun.util;

import java.io.Reader;
import java.sql.Clob;

import javax.sql.rowset.serial.SerialClob;

/**
 * 通用操作工具类
 * 
 * @author Wujun
 * @createTime 2011-6-5 上午09:48:53
 */
public class GerneralUtils {
	protected static int count = 0;

	/**
	 * 生成24位UUID
	 * 
	 * @return UUID 24bit string
	 */
	public static synchronized String getUUID() {
		count++;
		long time = System.currentTimeMillis();

		String uuid = "G" + Long.toHexString(time) + Integer.toHexString(count)
				+ Long.toHexString(Double.doubleToLongBits(Math.random()));

		uuid = uuid.substring(0, 24).toUpperCase();

		return uuid;
	}

	/**
	 * String处理
	 * 
	 * @param in
	 *            指定字符串
	 * @return 如果指定字符串为null,返回"",否则去空格返回
	 */
	public static String checkNull(String in) {
		String out = null;
		out = in;
		if (out == null || (out.trim()).equals("null")) {
			return "";
		} else {
			return out.trim();
		}
	}

	/**
	 * double类型取小数点后面几位
	 * 
	 * @param val
	 *            指定double型数字
	 * @param precision
	 *            取前几位
	 * @return 转换后的double数字
	 */
	public static Double roundDouble(double val, int precision) {
		Double ret = null;
		try {
			double factor = Math.pow(10, precision);
			ret = Math.floor(val * factor + 0.5) / factor;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 检测传入字符串是否为null或者空字符串
	 */
	public static boolean isNULLOrEmptyOfProperty(String source) {
		return null == source || source.length() == 0 || source.trim().equals("");
	}

	/**
	 * 将Clob对象转换成字符串
	 */
	public static String clobToString(Clob clob) {
		if (null == clob) {
			return "";
		}
		StringBuffer sb = new StringBuffer(65535);  //64K
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];//每次读取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b,0,i);
			}
		} catch (Exception ex) {
			sb = new StringBuffer();
		} finally {
			try {
				if (null != clobStream){
					clobStream.close();
				}
			} catch (Exception e) {
				throw new RuntimeException("Reader关闭异常");
			}
		}
		if (null == sb) {
			return "";
		} else {
			return sb.toString();
		}
	}
	/**
	 * 
	 * 将String转成Clob
	 * 
	 * @param str
	 * @return clob对象，如果出现错误，返回 null
	 * 
	 */
	public static Clob stringToClob(String str) {
		if (null == str) {
			return null;
		} else {
			try {
				Clob c = new SerialClob(str.toCharArray());
				return c;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
