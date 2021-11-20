package com.jun.plugin.xml.dom4j;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import com.jun.plugin.xml.domain.Book;

public class Dom4jTest {
	
	@Test
	public void create() throws Exception{
		File xmlFile=new File("D:/data.xml");
		Document doc = new SAXReader().read(xmlFile);
		
		//�����޸�Document��������
		//����޸ĺ��Document����д����xml�ĵ���
		//ָ���ļ������λ��
		FileOutputStream out = new FileOutputStream("d:/data.xml");
		//1.����д������
		XMLWriter writer = new XMLWriter(out);
		
		//2.д������
		writer.write(doc);
		//3.�ر���
		writer.close();
	}
	

//	@Test
	public void query() throws Exception {

		// 需要List 存放所有的book对象
		List allBook = new ArrayList();

		// 获得解析流
		SAXReader reader = new SAXReader();
		// xml文件的解析
		Document document = reader.read("D:/books.dom4j.xml");
		// 获得根元素
		Element rootElement = document.getRootElement();
		// 获得所有的书籍
		List list = rootElement.elements();
		// 遍历所有的书籍 -- list
		for (int e = 0; e < list.size(); e++) {
			// 创建book对象
			Book book = new Book();
			// 获得每一本book元素
			Element bookElement = (Element) list.get(e);
			// 获得书籍的id属性值
			String id = bookElement.attributeValue("id");
			// System.out.println(id);
			book.setId(id);

			// 获得title和price
			List childList = bookElement.elements();
			// 遍历子元素
			for (int c = 0; c < childList.size(); c++) {
				// 获得每一个子元素
				Element child = (Element) childList.get(c);
				// System.out.println(child);
				// 获得子元素文本内容
				String content = child.getText();
				// 判断是否是title
				if ("title".equals(child.getName())) {
					book.setTitle(content);
				}
				// 判断是否是price
				if ("price".equals(child.getName())) {
					book.setPrice(content);
				}

			}

			// 将已经封装了内容的book对象，添加到list中
			allBook.add(book);
		}

		// 程序解析前，输出内容
		System.out.println(allBook);

	}
	


	
//	@Test
	public void loadAndCreate() throws Exception {
		
		//获得document
		//获得解析流
		SAXReader reader = new SAXReader();
		//解析xml
		Document document  = reader.read("D:/books.dom4j.xml");
		
		//获得根元素
		Element rootElement = document.getRootElement();
		
		//添加
		//创建book元素
		Element newBook = DocumentHelper.createElement("book");
		
		//创建book元素的id属性
		Attribute  idAttr = DocumentHelper.createAttribute(newBook, "id", "b0041");
		//添加到book元素中
		newBook.add(idAttr);
		
		
		//创建title元素
		Element titleElement = DocumentHelper.createElement("title");
		//设置值
		titleElement.setText("凤姐玉照");
		
		//添加到newbook
		newBook.add(titleElement);
		
		
		
		//将新book元素添加到root元素
		rootElement.add(newBook);
		
		
		
		Element rootElement2 = document.getRootElement();
		Element book =DocumentHelper.createElement("book");
		Attribute attr=DocumentHelper.createAttribute(book, "id", "0001");
		book.add(attr);
		Element element = DocumentHelper.createElement("title");
		element.setText("aaaaaaaaa");
		book.add(element);
		Element element2 = DocumentHelper.createElement("name");
		element2.setText("abc");
		book.add(element2);
		rootElement2.add(book);
		
		
		//将document保存
		//持久化流
		
		//创建输出文件的位置
		FileOutputStream out = new FileOutputStream("D:/books.dom4j.xml");
		
		XMLWriter writer = new XMLWriter(out);
		//添加内容对象
		writer.write(document);
		//关闭流
		writer.close();
		
		
		
	}
	
//	@Test
	public void delete() throws Exception {
		
		//获得document
		//获得解析流
		SAXReader reader = new SAXReader();
		//解析xml
		Document document  = reader.read("D:/books.dom4j.xml");
		
		//删除 b002
		Node bookNode = document.selectSingleNode("//book[@id='0001']");
		//获得父节点
		Node parent = bookNode.getParent();
		Element parentElement = (Element) parent;
		
		//删除操作
		parentElement.remove(bookNode);
		
		//将document保存
		//持久化流
		
		//创建输出文件的位置
		FileOutputStream out = new FileOutputStream("D:/books.dom4j.xml");
		
		XMLWriter writer = new XMLWriter(out);
		//添加内容对象
		writer.write(document);
		//关闭流
		writer.close();
		
		
		
	}
	
	
//	@Test
	public void update() throws Exception {
		
		//获得document
		//获得解析流
		SAXReader reader = new SAXReader();
		//解析xml
		Document document  = reader.read("D:/books.dom4j.xml");
		
		//修改 b002 price 100
		Node bookNode = document.selectSingleNode("//book[@id='b002']");
		//强转转换
		Element bookElement = (Element) bookNode;
		//通过指定的名称获得相应的元素
		Element priceElement = bookElement.element("price");
		//修改值
//		priceElement.getText();
		priceElement.setText("100000000000");
		
		
		//将document保存
		//持久化流
		
		//创建输出文件的位置
		FileOutputStream out = new FileOutputStream("D:/books.dom4j.xml");
		
		XMLWriter writer = new XMLWriter(out);
		//添加内容对象
		writer.write(document);
		//关闭流
		writer.close();
		
	}

//	@Test
	public void getNodeWithXpath() throws Exception {
		
		//获得document
		//获得解析流
		SAXReader reader = new SAXReader();
		//解析xml
		Document document  = reader.read("D:/books.dom4j.xml");
		
		//查询book id = b002 的元素   java.lang.NoClassDefFoundError
		Node node = document.selectSingleNode("//book[@id='b002']"); 
		
		System.out.println(node);
		
	}


}
