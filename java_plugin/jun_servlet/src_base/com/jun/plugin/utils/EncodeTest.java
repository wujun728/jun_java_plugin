package com.jun.plugin.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodeTest {

	
	public static void main(String[] args) throws Exception {
		//
		String data = "中文";
		//编码
//		String encode = URLEncoder.encode(data,"UTF-8");
//		System.out.println(encode);
		//解码
//		String decode = URLDecoder.decode(encode, "UTF-8");
//		System.out.println(decode);
		
		//手动编码  byte[] --> String
		byte[] bytes = data.getBytes("UTF-8");  // [12,34,56]
		//生成一个字符串  "12,34,56"
		StringBuffer buf = new StringBuffer();
		for(int i = 0 ; i < bytes.length ; i ++){
			buf.append(bytes[i]).append(",");
		}
		//将最后一个“,”删除
		buf.deleteCharAt(buf.lastIndexOf(","));
		String bufData = buf.toString();
		System.out.println(bufData);
		
		
		//手动解码  bufData String -- byte[]
		String[] strs = bufData.split(",");
		//String[] -- byte[]
		byte[] bs = new byte[strs.length];
		for(int i = 0 ; i < strs.length ; i ++){
			bs[i] = new Byte(strs[i]);
		}
		System.out.println(bs);
		System.out.println(new String(bs,"UTF-8"));
	}
}
