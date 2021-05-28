package org.typroject.tyboot.prototype.account;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: AccountOperateRule.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: AccountOperateRule.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public interface AccountOperateRule {

	
	/**锁定账户的规则定义*/
	String RULE_OF_LOCK  = "";
	
	/**
	 * 检查当前操作是否可以进行
	 * @return
	 */
	boolean  checkOperation(Account account);
} 
