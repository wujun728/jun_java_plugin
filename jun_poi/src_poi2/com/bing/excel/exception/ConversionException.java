package com.bing.excel.exception;
/**  
 * 创建时间：2015-12-16下午6:19:12  
 * 项目名称：excel  
 * @author shizhongtao  
 * @version 1.0   
 * @since JDK 1.7
 * 文件名称：ConvertorException.java  
 * 类说明：  
 */
public class ConversionException extends RuntimeException {

	public ConversionException() {
		super();
	}

	public ConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConversionException(String message) {
		super(message);
	}

	public ConversionException(Throwable cause) {
		super(cause);
	}

}
