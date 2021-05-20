package com.jun.plugin.jdom;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class JDOMReader {

	public static void main(String[] args) throws Exception{
		SAXBuilder builder=new SAXBuilder();
		Document document=builder.build("src/main/resources/dom4j.xml");
		Element users=document.getRootElement();
		List userList=users.getChildren("user");
		for(int i=0;i<userList.size();i++){
			Element user=(Element)userList.get(i);
			String id=user.getAttributeValue("id");
			String name=user.getChildText("name");
			String sex=user.getChildText("sex");
			String age=user.getChildText("age");
			System.out.println("id="+id+";name="+name+";sex="+sex+";age="+age);
		}
	}
}
