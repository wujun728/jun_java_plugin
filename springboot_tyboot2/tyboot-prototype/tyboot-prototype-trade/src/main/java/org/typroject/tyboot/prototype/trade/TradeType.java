package org.typroject.tyboot.prototype.trade;

public interface TradeType {

	
	/**
	 * 交易类型
	 * @return
	 */
	String getType();
	
	
	/**
	 * 交易类型处理器beanName
	 * @return
	 */
	String getTradeProcessor();
	
	/**
	 * 交易类型中文名
	 * @return
	 */
	String parseString();
	
}
