package org.myframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.jdom.Document;

/**
 * 提供不同的结构类型之间的数据转换成JSON 
 * (XML,MAP,POJO ) -- >JSON
 * 
 * @author Administrator
 * 
 */
public class JsonUtil {
	/**
	 * 将xml字符串转换为JSON对象
	 * 
	 * @param xmlFile
	 *            xml字符串
	 * @return JSON对象
	 */
	public JSON getJSONFromXml(String xmlString) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(xmlString);
		return json;
	}

	/**
	 * 将xmlDocument转换为JSON对象
	 * 
	 * @param xmlDocument
	 *            XML Document
	 * @return JSON对象
	 */
	public JSON getJSONFromXml(Document xmlDocument) {
		String xmlString = xmlDocument.toString();
		return getJSONFromXml(xmlString);
	}

	/**
	 * 将xml字符串转换为JSON字符串
	 * 
	 * @param xmlString
	 * @return JSON字符串
	 */
	public String getJSONStringFromXml(String xmlString) {
		return getJSONFromXml(xmlString).toString();
	}

	/**
	 * 将xmlDocument转换为JSON字符串
	 * 
	 * @param xmlDocument
	 *            XML Document
	 * @return JSON字符串
	 */
	public String getJSONStringFromXMl(Document xmlDocument) {
		return getJSONStringFromXml(xmlDocument.toString());
	}

	/**
	 * 读取XML文件准换为JSON字符串
	 * 
	 * @param xmlFile
	 *            XML文件
	 * @return JSON字符串
	 */
	public String getJSONStringFromXMLFile(String xmlFile) {
		InputStream is = JsonUtil.class.getResourceAsStream(xmlFile);
		String xml;
		JSON json = null;
		try {
			xml = IOUtils.toString(is);
			XMLSerializer xmlSerializer = new XMLSerializer();
			json = xmlSerializer.read(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 将Java对象转换为JSON格式的字符串
	 * 
	 * @param javaObj
	 *            POJO,例如日志的model
	 * @return JSON格式的String字符串
	 */
	public static String getJsonStringFromJavaPOJO(Object javaObj) {
		return JSONObject.fromObject(javaObj).toString(1);
	}

	/**
	 * 将Map转换为JSON字符串
	 * 
	 * @param map
	 * @return JSON字符串
	 */
	public static String getJsonStringFromMap(Map<?, ?> map) {
		JSONObject object = JSONObject.fromObject(map);
		return object.toString();
	}
	
	/**
	 * 将Map转换为JSON字符串
	 * 
	 * @param map
	 * @return JSON字符串
	 */
	public static String getJsonStringFromXml(Map<?, ?> map) {
		JSONObject object = JSONObject.fromObject(map);
		return object.toString();
	}
	
	public static void main(String[] args) {
		String jsString = "{'a':'1'}";
	}
}
