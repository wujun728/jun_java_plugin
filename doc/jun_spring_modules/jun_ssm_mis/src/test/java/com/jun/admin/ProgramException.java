/*
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of Aspire
 *  Info, Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Aspire.
 */
package com.jun.admin;

/**
 * 由于编程或环境错误导致的异常。
 * <p>
 * 该异常继承自RuntimeException，因此不需要在方法的Throws中声明，
 * 该异常由全局异常处理程序统一处理，程序中不需要对该异常进行处理。
 * 
 * 该异常主要应于下面场合：在业务层或数据层在调用第三方类库时，将
 * 第三方类库抛出的由环境异常或编程错误导致的Exception转化成改异常，
 * 以便能够统一处理该类异常。
 * </p>
 */
public class ProgramException extends RuntimeException {
	private static final long serialVersionUID = 1913263321312485172L;

	/**
	 * @param cause
	 */
	public ProgramException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	/**
	 * @param message
	 */
	public ProgramException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProgramException(String message, Throwable cause) {
		super(message, cause);
	}

}
