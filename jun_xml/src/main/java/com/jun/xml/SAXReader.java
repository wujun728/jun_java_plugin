package com.jun.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXReader extends DefaultHandler{

	@Override
	public void startDocument() throws SAXException {
		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.print("\n ɨ���ĵ�����");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		System.out.print("<");
		System.out.print(qName);
		if(attributes!=null){
			for(int i=0;i<attributes.getLength();i++){
				System.out.print(" "+attributes.getQName(i)+"=\""+attributes.getValue(i)+"\"");
			}
		}
		System.out.print(">");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.print("</");
		System.out.print(qName);
		System.out.print(">");
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.print(new String(ch,start,length));
	}

	public static void main(String[] args) throws Exception{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parser=factory.newSAXParser();
		parser.parse("src/main/java/user.xml", new SAXReader());
	}
}
