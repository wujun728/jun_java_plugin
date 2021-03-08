package org.typroject.tyboot.prototype.account.trade;


/** 
 * 
 * <pre>
 *  Tyrest
 *  File: DefaultAccountTradeType.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  系统自身所支持的交易类型
 * 
 *  Notes:
 *  $Id: DefaultAccountTradeType.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public enum DefaultAccountTradeType implements AccountTradeType {


	/**
	 *	支付
	 */
	PAYMENT("支付","paymentHandler",""),
	
	/**
	 * 充值
	 */
	RECHARGE("充值","rechargeHandler",""),
	
	/**
	 * 从虚拟账户提现到外部账户
	 */
	CASHOUT("提现","cashoutHandler",""),


	/**
	 * 凍結
	 */
	FREEZE("凍結","freezeHandler",""),

	UNFREEZE("解冻","unfreezeHandler",""),

	UNFREEZE_DEST("转移冻结资金","",""),
	
	/**
	 * 用戶閒内部賬戶轉賬
	 */
	TRANSFER_INTERNAL ("内部转账","transferHandler","");
	
	
	
	
	private String operationName;
	private String accountTradeHandler;
	private String operationRule;
	
	DefaultAccountTradeType(String operationName ,String accountTradeHandler,String operationRule)
	{
		this.operationName = operationName;
		this.accountTradeHandler = accountTradeHandler;
		this.operationRule = operationRule;
	}

	

	public String getAccountTradeHandler() {
		return accountTradeHandler;
	}



	public String getOperationName()
	{
		return operationName;
	}



	@Override
	public String getAccountTradeType()
	{
		return this.name();
	}



	@Override
	public String getOperationRule()
	{
		
		return operationRule;
	}
	
	
	

	
}
