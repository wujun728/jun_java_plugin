package com.java.plugin.xml.rw.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jUtil {

	//1.���ڵõ�Document
	public static Document getDocument() throws Exception{
		//1.�õ�SAXReader��ȡ��
		SAXReader reader = new SAXReader();
		Document document = reader.read("src/exam.xml");
		return document;
	}
	
	//2.д��xml
	public static void write2Xml(Document document) throws Exception{
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/exam.xml"), format);
		writer.write(document);
	}
	
	
}
