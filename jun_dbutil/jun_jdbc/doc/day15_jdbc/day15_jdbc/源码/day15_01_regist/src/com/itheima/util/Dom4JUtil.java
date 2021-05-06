package com.itheima.util;

import java.io.FileOutputStream;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4JUtil {
	private static String xmlRealpath;
	static{
		ClassLoader cl = Dom4JUtil.class.getClassLoader();
		URL url = cl.getResource("users.xml");
		xmlRealpath = url.getPath();
	}
	
	public static Document getDocument() throws Exception{
		SAXReader reader = new SAXReader();
//		return reader.read("src/users.xml");//´íÎó
		return reader.read(xmlRealpath);
	}
	public static void write2xml(Document document) throws Exception{
		XMLWriter writer = new XMLWriter(new FileOutputStream(xmlRealpath), OutputFormat.createPrettyPrint());
		writer.write(document);
		writer.close();
	}
}
