package com.jun.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMReader2 {

	public static void printNodeAttr(Node node){
		NamedNodeMap namedNodeMap=node.getAttributes();
		for(int i=0;i<namedNodeMap.getLength();i++){
			Node attrNode=namedNodeMap.item(i);
			System.out.println(attrNode.getNodeName()+":"+attrNode.getFirstChild().getNodeValue());
		}
	}
	
	public static void main(String[] args) {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse("src/main/java/users.xml");
			NodeList nodeList=doc.getElementsByTagName("users");
			Element element=(Element)nodeList.item(0);
			NodeList usersNodeList=element.getElementsByTagName("user");
			for(int i=0;i<usersNodeList.getLength();i++){
				Element e=(Element)usersNodeList.item(i);
				System.out.println("name"+e.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
				printNodeAttr(e.getElementsByTagName("name").item(0));
				System.out.println("sex"+e.getElementsByTagName("sex").item(0).getFirstChild().getNodeValue());
				System.out.println("age"+e.getElementsByTagName("age").item(0).getFirstChild().getNodeValue());
				System.out.println("================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
