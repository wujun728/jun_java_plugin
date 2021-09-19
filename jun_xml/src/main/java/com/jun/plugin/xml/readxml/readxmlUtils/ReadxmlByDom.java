package com.jun.plugin.xml.readxml.readxmlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jun.plugin.xml.readxml.bean.Book;

/**
 * 用DOM方式读取xml文件
 * @author lune
 */
public class ReadxmlByDom {
	private static DocumentBuilderFactory dbFactory = null;
	private static DocumentBuilder db = null;
	private static Document document = null;
	private static List<Book> books = null;
	static{
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			db = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Book> getBooks(String fileName) throws Exception{
		//将给定 URI 的内容解析为一个 XML 文档,并返回Document对象
		document = db.parse(fileName);
		//按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList
		NodeList bookList = document.getElementsByTagName("book");
		books = new ArrayList<Book>();
		//遍历books
		for(int i=0;i<bookList.getLength();i++){
			Book book = new Book();
			//获取第i个book结点
			org.w3c.dom.Node node = bookList.item(i);
			//获取第i个book的所有属性
			NamedNodeMap namedNodeMap = node.getAttributes();
			//获取已知名为id的属性值
			String id = namedNodeMap.getNamedItem("id").getTextContent();//System.out.println(id);
			book.setId(Integer.parseInt(id));
			
			//获取book结点的子节点,包含了Test类型的换行
			NodeList cList = node.getChildNodes();//System.out.println(cList.getLength());9
			
			//将一个book里面的属性加入数组
			ArrayList<String> contents = new ArrayList<>();
			for(int j=1;j<cList.getLength();j+=2){
				
				org.w3c.dom.Node cNode = cList.item(j);
				String content = cNode.getFirstChild().getTextContent();
				contents.add(content);
				//System.out.println(contents);
			}
			
			book.setName(contents.get(0));
			book.setAuthor(contents.get(1));
			book.setYear(Integer.parseInt(contents.get(2)));
			book.setPrice(Double.parseDouble(contents.get(3)));
			books.add(book);
		}
		
		return books;
		
	}
	
	public static void main(String args[]){
		String fileName = "src/res/books.xml";
		try {
			List<Book> list = ReadxmlByDom.getBooks(fileName);
			for(Book book :list){
				System.out.println(book);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
