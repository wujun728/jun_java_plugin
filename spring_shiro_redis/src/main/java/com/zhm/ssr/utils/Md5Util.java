package com.zhm.ssr.utils;

import java.security.MessageDigest;

public class Md5Util {
	/**
	 * 对字符串进行加密处理
	 * 
	 * @param strString
	 * @return 加密后的字符串
	 */
	public static String stringByMD5(String strString) {
		if (strString != null) {
			try {
				// 创建MD5算法的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(strString.getBytes());
				// 将得到的字节数组变成字符串返回
				String returnStr = bytesToHexString(results);
				return returnStr;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
}
