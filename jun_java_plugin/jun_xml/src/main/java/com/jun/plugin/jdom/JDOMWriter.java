package com.jun.plugin.jdom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class JDOMWriter {

	public static void main(String[] args) {
		Element user=new Element("user");
		
		Attribute id=new Attribute("id", "001");
		Attribute aa=new Attribute("aa", "xx");
		user.setAttribute(id);
		user.setAttribute(aa);
		
		Element name=new Element("name");
		name.setText("nameText");
		user.addContent(name);
		
		Element sex=new Element("sex");
		sex.setText("性别");
		user.addContent(sex);
		
		Element age=new Element("age");
		age.setText("age");
		user.addContent(age);
		
		Document document=new Document(user);
		XMLOutputter out=new XMLOutputter();
		out.setFormat(out.getFormat().setEncoding("UTF-8"));
		
		try {
			out.output(document, new FileOutputStream("src/main/resources/jdomwriter.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
