package com.jun.plugin.core.utils.demo;

import java.io.IOException;

import com.jun.plugin.core.utils.io.FileUtil;

public class XmlUtilDemo {
	public static void main(String[] args) throws IOException {
//		Document doc = XmlUtil.createXml("root");
//		System.out.println(XmlUtil.toStr(doc, "gbk"));
		
		String xmlStr = FileUtil.readString("d:/aaa.xml", "gbk");
		System.out.println(xmlStr);
	}
}
