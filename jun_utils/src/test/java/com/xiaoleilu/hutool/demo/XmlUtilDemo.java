package com.xiaoleilu.hutool.demo;

import java.io.IOException;

import com.jun.plugin.utils2.io.FileUtil;

public class XmlUtilDemo {
	public static void main(String[] args) throws IOException {
//		Document doc = XmlUtil.createXml("root");
//		System.out.println(XmlUtil.toStr(doc, "gbk"));
		
		String xmlStr = FileUtil.readString("d:/aaa.xml", "gbk");
		System.out.println(xmlStr);
	}
}
