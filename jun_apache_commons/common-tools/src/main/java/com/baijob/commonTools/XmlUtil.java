package com.baijob.commonTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.xml.sax.SAXException;

/**
 * DOM工具类，处理XML
 * @author luxiaolei@baijob.com
 */
public class XmlUtil {
	/** 在XML中无效的字符 正则 */
	public final static String INVALID_REGEX = "[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]";
	
	/**
	 * 从字符串中获得DOM对象
	 * @param xmlString xml字符串
	 * @return DOM对象
	 * @throws DocumentException
	 */
	public static Document parseDoc(String xmlString) throws DocumentException{
		return DocumentHelper.parseText(cleanInvalid(xmlString));
	}
	
	/**
	 * 从文件获得DOM对象
	 * @param xmlPathBaseClassLoader 相对路径（相对于当前项目的classes路径）
	 * @return DOM 对象
	 * @throws DocumentException
	 */
	public static Document readDoc(String xmlPathBaseClassLoader) throws DocumentException{
		return new SAXReader().read(XmlUtil.class.getClassLoader().getResourceAsStream(xmlPathBaseClassLoader));
	}
	
	/**
	 * 从文件获得DOM对象
	 * @param xmlFile xml文件对象
	 * @return DOM对象
	 * @throws DocumentException
	 */
	public static Document readDoc(File xmlFile) throws DocumentException{
		return new SAXReader().read(xmlFile);
	}
	
	/**
	 * 从URL获得DOM对象
	 * @param url XML的URL
	 * @return DOM对象
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public static Document readDoc(URL url) throws DocumentException, IOException{
		return new SAXReader().read(url.openStream());
	}
	
	/**
	 * 创建一个空XML
	 * @param rootElementName 根节点名
	 * @return XML对象
	 */
	public static Document createDoc(String rootElementName){
		Document doc = DocumentHelper.createDocument();
		doc.addElement(rootElementName);
		return doc;
	}
	
	/**
	 * 写入XML到文件
	 * @param doc XML文档
	 * @param filePath 文件的路径
	 * @param charset 字符集
	 * @param isEscapeText 是否对特殊字符转义
	 * @throws IOException
	 */
	public static void xmlWriteToFile(Document doc, String filePath, String charset, boolean isEscapeText) throws IOException {
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding(charset);
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(FileUtil.getBufferedWriter(filePath, charset, false), outputFormat);
			writer.setEscapeText(isEscapeText);
			writer.write(doc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			//不会发生此异常（在getBufferedWriter方法中已经做了自动创建文件操作）
			e.printStackTrace();
		} finally {
			if(writer != null) writer.close();
		}
	}
	
	/**
	 * 检验指定的XML是否符合
	 * @param doc 被检验的XML文档
	 * @param schemePath schema的路径
	 * @return 如果出错，返回错误信息，否则返回null
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws DocumentException
	 */
	public static String validateBySchema(Document doc, String schemePath) throws ParserConfigurationException, SAXException, DocumentException{
		XMLErrorHandler errorHandler =  new XMLErrorHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		SAXParser parser = factory.newSAXParser();
		
		parser.setProperty(
                "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        parser.setProperty(
                "http://java.sun.com/xml/jaxp/properties/schemaSource",
                "file:" + schemePath);
        
        SAXValidator validator = new SAXValidator(parser.getXMLReader());
        validator.setErrorHandler(errorHandler);
        validator.validate(doc);
        
        if (errorHandler.getErrors().hasContent()) {
        	return errorHandler.getErrors().asXML();
        }
		return null;
	}
	
	/**
	 * 去除XML文本中的无效字符
	 * @param xmlContent XML文本
	 * @return 当传入为null时返回null
	 */
	public static String cleanInvalid(String xmlContent){
		if (xmlContent == null) return null;
		return xmlContent.replaceAll(INVALID_REGEX, "");  
	}
}
