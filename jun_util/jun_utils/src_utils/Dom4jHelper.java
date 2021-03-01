/**
 * 
 */
package com.gootrip.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author advance
 *
 */
public class Dom4jHelper {
	/**
	 *  解析url xml文档
	 * @param url
	 * @return
	 * @throws DocumentException
	 */
    public Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }
    /**
     * 遍历解析文档
     * @param document
     */
    public void treeWalk(Document document) {
        treeWalk( document.getRootElement() );
    }
    /**
     * 遍历解析元素
     * @param element
     */
    public void treeWalk(Element element) {
        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
            Node node = element.node(i);
            if ( node instanceof Element ) {
                treeWalk( (Element) node );
            }
            else {
                // 处理....
            }
        }
    }

	/** 
	 * 解析文件，获得根元素
	 * @param xmlPath
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static Element parse(String xmlPath,String encoding)throws Exception{
		//文件是否存在
		File file = new File(xmlPath);
        if(!file.exists()){
        	throw new Exception("找不到xml文件："+xmlPath);
        }
        
		//解析
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(new FileInputStream(file),encoding);
		Element root = doc.getRootElement();
		return root;
	}
	
	/**
	 * 保存文档
	 * @param doc
	 * @param xmlPath
	 * @param encoding
	 * @throws Exception
	 */
	public static void save(Document doc,String xmlPath,String encoding)throws Exception{
		OutputFormat format=OutputFormat.createPrettyPrint();
	    format.setEncoding(encoding);
	    XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(xmlPath),encoding),format);
		writer.write(doc);
		writer.flush();
		writer.close();	
	}
	/**
	 * 修改xml某节点的值
	 * @param inputXml 原xml文件
	 * @param nodes 要修改的节点
	 * @param attributename 属性名称
	 * @param value 新值
	 * @param outXml 输出文件路径及文件名 如果输出文件为null，则默认为原xml文件
	 */
	public static void modifyDocument(File inputXml, String nodes, String attributename, String value, String outXml) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputXml);
			List list = document.selectNodes(nodes);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
				if (attribute.getName().equals(attributename))
					attribute.setValue(value);
			}
			XMLWriter output;
			if (outXml != null){ //指定输出文件
				output = new XMLWriter(new FileWriter(new File(outXml)));
			}else{ //输出文件为原文件
				output = new XMLWriter(new FileWriter(inputXml));
			}
			output.write(document);
			output.close();
		}

		catch (DocumentException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	/**
	 * xml转换为字符串
	 * @param doc
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String toString(Document doc,String encoding)throws Exception{
		OutputFormat format=OutputFormat.createPrettyPrint();
	    format.setEncoding(encoding);
	    ByteArrayOutputStream byteOS=new ByteArrayOutputStream();
	    XMLWriter writer = new XMLWriter(new OutputStreamWriter(byteOS,encoding),format);
		writer.write(doc);
		writer.flush();
		writer.close();		
		writer=null;
		
		return byteOS.toString(encoding);
	}
	/**
	 * 字符串转换为Document
	 * @param text
	 * @return
	 * @throws DocumentException
	 */
	public static Document str2Document(String text) throws DocumentException{
		Document document = DocumentHelper.parseText(text);
        return document;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
