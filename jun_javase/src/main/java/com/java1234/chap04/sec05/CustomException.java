package com.java1234.chap04.sec05;

/**
 * 自定义异常，继承自Exception
 * @author caofeng
 *
 */
public class CustomException extends Exception{

	public CustomException(String message){
		super(message);
	}
}
