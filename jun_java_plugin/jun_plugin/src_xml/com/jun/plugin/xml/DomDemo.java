package com.jun.plugin.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DomDemo {
	@Test
	public void aa(){
		Integer a = 128;
		Integer b = 128;
		System.err.println(a.equals(b));
	}
	
	
	InputStream in;
	@Before
	public void before(){
		in = DomDemo.class.getClassLoader()
				.getResourceAsStream("users.xml");
	}
	
	@Test
	public void jaxpDom() throws Exception{
		DocumentBuilder db=DocumentBuilderFactory.newInstance()
						   .newDocumentBuilder();
		Document dom = db.parse (DomDemo.class.getClassLoader()
							.getResourceAsStream("users.xml"));
		NodeList nl = dom.getElementsByTagName("user");
		int len = nl.getLength();
		for(int i=0;i<len;i++){
			Element el =  (Element) nl.item(i);
			String id = el.getAttribute("id");
			String name = el.getElementsByTagName("name")
								.item(0).getTextContent();
			String age = el.getElementsByTagName("age")
								.item(0).getTextContent();
			System.err.println("id:"+id+","+name+","+age);
		}
	}
	@Test
	public void jaxpSax() throws Exception{
		SAXParser sax = SAXParserFactory.newInstance().newSAXParser();
		sax.parse(in,new DefaultHandler(){
			private boolean nameOrAge=false;
			/**
			 * 某个元素开始了<somename>
			 */
			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attr) throws SAXException {
				if(qName.equals("user")){
					System.err.println(attr.getValue("id"));
				}else if(qName.equals("name") || qName.equals("age")){
					nameOrAge=true;
				}
			}
			/**
			 * 某个元素的结束</somename>
			 */
			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				if(qName.equals("name") || qName.equals("age")){
					nameOrAge=false;
				}
			}
			/**
			 * 字符的开始<>someValue</>
			 */
			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				if(nameOrAge){
					String value = new String(ch,start,length);
					System.err.println(value);
				}
			}
		});
	}
	/**
	 * pull解析
	 * javax.xml.stream
	 */
	@Test
	public void jaxpStax() throws Exception{
		XMLStreamReader xml = 
				XMLInputFactory.newInstance().createXMLStreamReader(in);
		int tag = xml.next();
		while(tag!=XMLStreamReader.END_DOCUMENT){
			if(tag==XMLStreamReader.START_ELEMENT){
				if(xml.getLocalName().equals("user")){
					String id = xml.getAttributeValue(0);
					System.err.println(id);
				}
				if(xml.getLocalName().equals("name")){
					String name = xml.getElementText();
					System.err.println("name:"+name);
				}
				if(xml.getLocalName().equals("age")){
					String age = xml.getElementText();
					System.err.println("age:"+age);
				}
			}
			tag = xml.next();
		}
	}
	
}
