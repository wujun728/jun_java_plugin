package com.bing.excel.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author shizhongtao
 *
 */
public interface BingExcelEvent {

	
	<T> void readFile(File file, Class<T> clazz, int startRowNum,BingReadListener listener) throws Exception ;
	/**
	 * 根据condition条件读取相应的sheet到list对象
	 * @param file
	 * @param condition
	 * @param listener
	 * @return
	 */
	<T> void readFile(File file, ReaderCondition<T> condition,BingReadListener listener) throws Exception ;

	
	 /**
	  * 读取所有sheet表格，到list
	 * @param file
	 * @param conditions 每个表格对应的condition
	 * @return
	 */
	void readFileToList(File file,ReaderCondition[] conditions,BingReadListener listener) throws Exception;
	 
	 

	/**
	 * 读取第一个sheet到SheetVo
	 * @param stream
	 * @param condition
	 * @return
	 */
	<T> void readStream(InputStream stream,ReaderCondition<T> condition,BingReadListener listener) throws Exception;
	<T> void readStream(InputStream stream,Class<T> clazz, int startRowNum,BingReadListener listener) throws Exception;

	 void readStreamToList(InputStream stream,  ReaderCondition[] condition,BingReadListener listener) throws Exception;
	
	 
	 BingWriterHandler writeFile(File file)throws FileNotFoundException;
		
	 BingWriterHandler  writeFile(String path);
	// BingWriterHandler  writeFile(OutputStream stream);
}
