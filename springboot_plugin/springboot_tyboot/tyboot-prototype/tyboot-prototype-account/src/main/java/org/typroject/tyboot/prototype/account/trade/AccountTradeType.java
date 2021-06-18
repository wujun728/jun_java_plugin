package org.typroject.tyboot.prototype.account.trade;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: AccountTradeType.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  虚拟账户交易类型
 *  可通过此接口扩展更多的交易类型
 *  Notes:
 *  $Id: AccountTradeType.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public interface AccountTradeType {

	
	/**
	 * 
	 * 交易类型名称
	 *
	 * @return
	 */
	public String getOperationName();
	
	
	/**
	 * 
	 * 交易类型标识code
	 *
	 * @return
	 */
	public String getAccountTradeType();
	
	/**
	 * 
	 * 交易处理类beanName
	 *
	 * @return
	 */
	public String getAccountTradeHandler();
	
	/**
	 * 
	 * 执行交易的前提规则
	 *
	 * @return
	 */
	public String getOperationRule();
	
}
