package com.jun.plugin.dom4j;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DOM4JReader {

	public static void main(String[] args) throws Exception{
		SAXReader saxReader=new SAXReader();
		Document document=saxReader.read("src/main/resources/dom4j.xml");
		Element rootElement=document.getRootElement();
		Iterator iter=rootElement.elementIterator();
		while(iter.hasNext()){
			Element userElement=(Element)iter.next();
			System.out.println("id"+userElement.attributeValue("id"));
			System.out.println("name"+userElement.elementText("name"));
			System.out.println("sex"+userElement.elementText("sex"));
			System.out.println("age"+userElement.elementText("age"));
			System.out.println("=========");
		}
	}
}
