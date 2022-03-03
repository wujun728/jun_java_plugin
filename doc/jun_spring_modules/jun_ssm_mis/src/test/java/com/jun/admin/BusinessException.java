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
 * 业务逻辑异常 。
 * <p>
 * 业务层的每个方法都可能抛出该异常，该异常一般是在Action层的入口方法中捕获处理， 业务层没有必须的理由不要处理该异常。
 * </p>
 * <p>
 * 注意：BusinessException类及子类使用时，最好使用
 * BusinessException(String errorCode, String errorMessage)构造函数。
 * 没有充分的理由，请不要使用BusinessException(String message, Throwable
 * cause) 构造函数。
 * 
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 2678145046723789238L;
	private String errorCode = null;
	private String message = null;

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 返回详细的错误消息。
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            - 错误消息码
	 * @param errorMessage
	 *            - 错误消息参数
	 */
	public BusinessException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;

		this.message = "【" + errorCode + "】" + errorMessage;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause) {
		super(cause);

		this.message = message;
	}

}
