/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.exception;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年5月6日 下午3:44:52
 */
public class OsmpWebServiceException extends RuntimeException {

	private static final long serialVersionUID = 1628481414427561816L;

	public OsmpWebServiceException(String message) {
		super(message);
	}

	public OsmpWebServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
