package org.typroject.tyboot.prototype.trade;
/**  
 * 
 * <pre>
 *  Tyrest
 *  File: DefaultTradeType.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 * 
 *  Notes:
 *  $Id: DefaultTradeType.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public enum DefaultTradeType  implements TradeType{
	
	
	
	/**
	 *	订单支付
	 */
	PAYMENT("Payment"),
	
	/**
	 * 虚拟账户充值
	 */
	RECHARGE("Recharge"),
	
	/**
	 * 订单退款
	 */
	REFUND("Refund"),
	
	/**
	 * 从虚拟账户提现到外部账户
	 */
	CASHOUT("Cashout"),

	
	/**
	 * 虚拟账户内部转账
	 */
	TRANSFER("Transfer"),


	/**
	 * 凍結賬戶資金
	 */
	FREEZE("Freeze");
	

	 
	
	
	private String tradeProcessor;
	
	
	private DefaultTradeType (String tradeProcessor)
	{
		this.tradeProcessor = tradeProcessor;
	}
	
	public String getTradeProcessor() {
		return tradeProcessor;
	}

	public String parseString()
	{
		String str = "";
		switch(this)
		{
			case PAYMENT:
				str = "支付";
				break;
			case RECHARGE:
				str = "充值";
				break;
			case REFUND:
				str = "退款";
				break;
			case CASHOUT:
				str = "提现";
			case TRANSFER:
				str = "转账";
				break;
			default :
				str = "";
		}
		return str;
	}
	
	
	
	public static DefaultTradeType getInstance(String str)
	{
		DefaultTradeType type = null;
		
		for(DefaultTradeType ut : DefaultTradeType.values())
		{
			if(ut.toString().equals(str))
			{
				type = ut;
			}
		}
		
		return type;
		
	}



	@Override
	public String getType() {
		return this.name();
	}



	
	
}

/*
*$Log: av-env.bat,v $
*/