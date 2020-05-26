package com.jun.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMReader {

	public static void main(String[] args) {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse("src/main/java/user.xml");
			NodeList nodeList=doc.getElementsByTagName("user");
			Element e=(Element)nodeList.item(0);
			System.out.println("name"+e.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
			System.out.println("sex"+e.getElementsByTagName("sex").item(0).getFirstChild().getNodeValue());
			System.out.println("age"+e.getElementsByTagName("age").item(0).getFirstChild().getNodeValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
