package org.typroject.tyboot.prototype.account;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: AccountStatus.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  虚拟账户状态定义
 * 
 *  Notes:
 *  $Id: AccountStatus.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public enum AccountStatus {
	
	
	/**
	 * 未初始化，即账户为创建，当前状态的虚拟账户对象只能执行初始化操作之后才能进行其他交易，
	 */
	NEW,
	
	/**
	 * 正常：可正常进行各种交易
	 */
	NORMAL,
	
	/**
	 * 锁定
	 */
	LOCKED,
	
	
	/**
	 * 失效
	 */
	INVALID;

}
