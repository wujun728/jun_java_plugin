
package com.jun.plugin.jdom;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class JDOMDemo {

	public static void parseXML(String path) throws Exception{
		SAXBuilder builder = new SAXBuilder() ;
		Document document = builder.build(new File(path)) ;
		Element root = document.getRootElement() ;
		List<Element> allplus = root.getChildren("plus") ;
		Iterator<Element> iter= allplus.iterator() ;
		while(iter.hasNext()){
			Element plus = iter.next() ;
			Element id = plus.getChild("id") ;
			Element title = plus.getChild("title") ;
			System.out.println(id.getTextTrim()+"-------"+title.getTextTrim());
		}
	}
	/*public static void createXML(String path) throws Exception{
		Document document = new Document();
		Element root = new Element("allplus") ;
		List<Areaplus> list = ServiceFactory.getIAreaplusServiceinstance().listByUpid(35) ;
		Iterator<Areaplus> iter = list.iterator() ;
		while(iter.hasNext()){
			Areaplus ap = iter.next() ;
			Element plus = new Element("plus") ;
			Element id = new Element("id") ;
			Element title = new Element("title") ;
			//��JDOM�������в���Ҫ�����ı��ڵ㣬����ֱ�Ӹ�ֵ
			id.addContent(ap.getId().toString()) ;
			title.addContent(ap.getTitle()) ;
			plus.addContent(id);
			plus.addContent(title) ;
			root.addContent(plus) ;
		}
		document.setRootElement(root) ;
		FileWriter out = new FileWriter(new File(path)) ;
		XMLOutputter outputter = new XMLOutputter() ;
//		outputter.setEncoding("GBK") ;
		outputter.output(document, out) ;
		out.close() ;
	}*/
	public static void main(String[] args) throws Exception {
		parseXML("D:/test2.xml") ;
//		createXML("D:/test2.xml") ;
	}
}
