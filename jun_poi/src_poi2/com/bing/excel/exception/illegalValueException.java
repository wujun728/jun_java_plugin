package com.bing.excel.exception;
/**  
 * 创建时间：2015-12-15下午3:52:43  
 * 项目名称：excel  
 * @author shizhongtao  
 * @version 1.0   
 * @since JDK 1.7
 * 文件名称：ErrorValueException.java  
 * 类说明：  
 */
public class illegalValueException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public illegalValueException() {
		super("单元格数值非法");
	}

	public illegalValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public illegalValueException(String message) {
		super(message);
	}

}
