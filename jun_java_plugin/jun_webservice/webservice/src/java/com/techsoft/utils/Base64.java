/**
 *  Copyright (c) 2012-2022 徐国军. All rights reserved.
 *  
 *  使用开源协议LGPL, 可以商用，但不能申请此平台的版权
 *  
 *  关于LGPL协议 请参考 http://www.oschina.net/question/12_2827
 *  
 *  email: chinaxuguojun@163.com QQ: 1632619065
 */

package com.techsoft.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64 {
	public static String Encode(String source) {
		return org.apache.axiom.om.util.Base64.encode(source.getBytes());
	}

	public static String Decode(String source) {
		byte[] bytes = org.apache.axiom.om.util.Base64.decode(source);
		return new String(bytes);
	}

	public static byte[] DecodeBytes(String source) {
		return org.apache.axiom.om.util.Base64.decode(source);
	}

	public static String Encode(InputStream source) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(10240);
		try {
			byte[] tempbytes = new byte[1024];
			int len = source.read(tempbytes);
			while (len != -1) {
				byteStream.write(tempbytes, 0, len);
				len = source.read(tempbytes);
			}

			byte[] bytes = byteStream.toByteArray();

			return org.apache.axiom.om.util.Base64.encode(bytes);
		} finally {
			byteStream.close();
		}
	}

	public static String Encode(byte[] bytes) {
		return org.apache.axiom.om.util.Base64.encode(bytes);
	}
}
