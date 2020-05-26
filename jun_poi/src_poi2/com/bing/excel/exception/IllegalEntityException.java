package com.bing.excel.exception;

/**
 * 当用户定义model不符合需要时候抛出异常
 * @author shizhongtao
 * @time 
 */
public class IllegalEntityException extends RuntimeException {
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public IllegalEntityException(Class<?> clz,String message) {
		super("The model entity ["+clz.getName()+"]："+message);
		
	}

	public IllegalEntityException(String message, Throwable cause) {
		super(message, cause);
	}
	public IllegalEntityException(Class clazz,String message, Throwable cause) {
		super("The model entity ["+clazz.getName()+"]："+message,cause);
	}

	public IllegalEntityException(String message) {
		super(message);
	}

	public IllegalEntityException(Throwable cause) {
		super(cause);
	}
	
}
