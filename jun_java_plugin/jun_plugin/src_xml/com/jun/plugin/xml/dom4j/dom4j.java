package com.jun.plugin.xml.dom4j;


//使用dom4j解析XML文档：
/*
 * Dom4j解析是主要是通过SAXReader获取Document，然后用Document对XML文档进行各种操作；
 不论XML文档中指定的是什么编码，SAXReader在读取时都会将XML文档格式化为UTF-8编码；
 如果低层流是字节流，则XMLWriter会根据指定或默认编码将XML转为字节流并交给低层流；
 XMLWriter输出时默认编码是UTF-8，可通过指定格式化输出器来指定XMLWriter将输出的XML文档格式化为何种编码；
 如果低层流是字符流，则XMLWriter默认会直接将XML交给低层流，然后低层字符流会以自己或指定的编码将XML解析并输出；
 也可指定格式化输出器来将XML转为指定编码再交给低层字符流；
 15.0使用dom4j解析XML文档：

 如1： XMLWriter writer = new XMLWriter(new FileWriter("F://java project//javaWeb//day_02//demo1.xml"));
 writer.write(document); 	
 writer.close();
 乱码，且不论原XML中的encoding是什么编码，输出后的XML总是UTF-8；因为SAXReader读入XML时已将其格式化为UTF-8了,
 而FileWriter是以本地编码输出的；


 如2：XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("F://java project//javaWeb//day_02//demo1.xml"),"UTF-8"));
 无乱码正常输出； OutputStreamWriter是字符流通向字节流的桥梁；OutputStreamWriter只能以指定编码将SAXReader读取的内容解析为字节流，
 而不会进行任何格式化操作；所以这里只能指定以UTF-8来解析，因为SAXReader读取的内容是UTF-8的；


 如3：OutputFormat format=OutputFormat.createPrettyPrint();   //格式化输出器；
 format.setEncoding("gb2312");

 XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(
 "F://java project//javaWeb//day_02//demo1.xml"),"gb2312"),format);
 正常输出，XML输出前后encoding相同；通过格式化输出器将SAXReader读取的内容格式化为gb2312，然后交给OutputStreamWriter，OutputStreamWriter以指定的编码将XML解析为字节流并交给FileOutputStream输出；

 如4：  XMLWriter writer = new XMLWriter(new FileOutputStream(
 "F://java project//javaWeb//day_02//demo1.xml"),format);
 正确输出；因为底层流是字节流，所以XMLWriter在输出时要先以指定编码将XML格式化为字节流交给低层流，然后底层流直接输出；
 * */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
//import org.junit.Test;
import org.junit.Test;

public class dom4j {
//	private String file = "D:\\workspace\\workspace_myeclipse_mine\\P_MINE_MVC\\demo\\com\\demo\\xml\\demo1.xml";
//	private String file = "demo1.xml";
//	private String file = "demo\\com\\demo\\xml\\demo1.xml";
	private String file = "D:/books.xml";

//	@Test
	// 读取属性
	public void read() throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));

		Element root = document.getRootElement();// 获取文档的根元素；

		// 获取根节点下指定名称的第二个子节点；
		Element book = (Element) root.elements("node").get(1);
		// 获取该节点下指定节点的指定属性的值；
		String value = book.element("name").attributeValue("sd");
		System.out.println(value);
	}

	@Test
	// 添加元素；
	public void add() throws DocumentException, IOException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));

		// 获取根节点下指定节点,如果多个指定节点，则获取第一项；
		Element book = document.getRootElement().element("node");
		// 为该节点添加亲子节点，并设置其内容；
		book.addElement("cnode").setText("love1"); // 默认添加到该元素中所有子元素之后；

		// 创建格式化输出器，并设置格式：
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");

		// 按格式 化输出器指定的格式输出
		XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document); // 输出
		writer.close();
	}

	@Test
	// 向指定位置添加元素
	public void add2() throws DocumentException, IOException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));

		// 获取根元素中指定名称的第一个元素；
		Element book = document.getRootElement().element("node");
		List list = book.elements(); // 获取该元素中子元素集；

		Element price = DocumentHelper.createElement("售价"); // 创建element对象；
		price.setText("12345666"); // 设置内容；
		list.add(2, price);// 添加，list中第二个位置的元素会自动后移；

		// 格式化输出器；
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");

		XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document); // 输出
		writer.close();
	}

	@Test
	// 删除
	public void delete() throws DocumentException, IOException, FileNotFoundException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));

		// 获取根元素中指定名称的子元素中指定名称的子元素；
		Element price = document.getRootElement().element("node").element("亲");
		price.getParent().remove(price);// 获取该元素的父元素，然后移除该元素；

		// 格式化输出器；
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");

		XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document); // 输出
		writer.close();
	}

//	@Test
	// 更新
	public void update() throws DocumentException, IOException, FileNotFoundException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(file));

		// 获取根元素中指定名称的子元素集的第二项；
		Element book = (Element) document.getRootElement().elements("node").get(1);//
		book.element("age").setText("无与伦比11");// 设置该元素中指定子元素的内容；

		// 格式化输出器；
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");

		XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document); // 输出
		writer.close();
	}

}
