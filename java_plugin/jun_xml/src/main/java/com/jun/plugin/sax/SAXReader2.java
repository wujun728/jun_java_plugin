package com.jun.plugin.sax;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.jun.plugin.model.User;

public class SAXReader2 extends DefaultHandler{
	private List<User> users=null;
	private User user=null;
	private String preTag=null; // �����һ���ڵ�����
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("开始");
		users=new ArrayList<User>();
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("\n 结束");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if("user".equals(qName)){
			user=new User();
			user.setId(attributes.getValue(0));
		}
		preTag=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("user".equals(qName)){
			users.add(user);
			user=null;
		}
		preTag=null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(preTag!=null){
			String content=new String(ch,start,length);
			if("name".equals(preTag)){
				user.setName(content);
			}else if("sex".equals(preTag)){
				user.setSex(content);
			}else if("age".equals(preTag)){
				user.setAge(Integer.parseInt(content));
			}
		}
	}

	public static void main(String[] args) throws Exception{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parser=factory.newSAXParser();
		SAXReader2 sax02=new SAXReader2();
		parser.parse("src/main/resources/dom4j.xml", sax02);
		for(User s:sax02.users){
			System.out.println(s);
		}
	}
}
