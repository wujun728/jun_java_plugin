package com.jun.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DOM4Writer {

	public static void main(String[] args) {
		Document document=DocumentHelper.createDocument();
		Element userElement=document.addElement("user");
		userElement.addAttribute("id", "001");
		userElement.addAttribute("aa", "xx");
		
		Element name=userElement.addElement("name");
		name.setText("张三111");
		Element sex=userElement.addElement("sex");
		sex.setText("233322");
		Element age=userElement.addElement("age");
		age.setText("20111");
		
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		try {
			XMLWriter writer=new XMLWriter(new FileOutputStream("src/main/java/user3.xml"),format);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
