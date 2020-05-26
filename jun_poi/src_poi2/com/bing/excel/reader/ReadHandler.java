package com.bing.excel.reader;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

/**
 * @author shizhongtao
 *
 * @date 2016-3-1
 * Description:  
 */
public interface ReadHandler {
	/**
	 * 处理所用数据对象
	 */
	void readSheets() throws IOException, OpenXML4JException , SAXException;
	void readSheets(int maxReadLine) throws IOException, OpenXML4JException , SAXException;
	/**
	 * 读取指定的数据
	 * @param index sheetDate对应下标 0 start
	 */
	void readSheet(int index)throws IOException, OpenXML4JException , SAXException;
	void readSheet(int[] indexs)throws IOException, OpenXML4JException , SAXException;
	void readSheet(String indexName)throws IOException, OpenXML4JException , SAXException;
	void readSheet(int index,int maxReadLine)throws IOException, OpenXML4JException , SAXException;
	void readSheet(int[] indexs,int maxReadLine)throws IOException, OpenXML4JException , SAXException;
	void readSheet(String indexName,int maxReadLine)throws IOException, OpenXML4JException , SAXException;
}
