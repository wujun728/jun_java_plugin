package org.typroject.tyboot.prototype.account.trade;

import org.typroject.tyboot.prototype.account.Account;

import java.util.Map;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: AccountTradeHandler.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 * 	虚拟账户交易处理
 * 
 *  Notes:
 *  $Id: AccountTradeHandler.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public interface AccountTradeHandler {
	
	
	
	
	/**
	 * 交易参数的类型
	 * 
	 */
	enum tradeParamType{String,Integer,AccountType,Long};


	/**
	 * 虛擬賬戶交易處理
	 * @param params	交易參數定義
	 * @param account	主賬戶對象(轉賬則為來源賬戶，凍結則是唄凍結的賬戶)
	 * @return
	 * @throws Exception
	 */
	boolean execute(Map<String, Object> params, Account account)throws Exception;


}

