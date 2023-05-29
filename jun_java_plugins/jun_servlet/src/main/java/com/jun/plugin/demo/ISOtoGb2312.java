package com.jun.plugin.demo;

public class ISOtoGb2312 {
	public static String ISOtoGb2312(String str) {
		try {
			byte[] bytesStr = str.getBytes("ISO-8859-1");
			return new String(bytesStr, "GB2312");
		} catch (Exception ex) {
			return str;
		}
	}
}