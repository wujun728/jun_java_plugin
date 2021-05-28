package org.typroject.tyboot.prototype.trade.channel;

import org.typroject.tyboot.prototype.trade.TradeResultModel;
import org.typroject.tyboot.prototype.trade.TradeStatus;
import org.typroject.tyboot.prototype.trade.TradeType;
import org.typroject.tyboot.face.trade.model.TransactionsRecordModel;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.prototype.trade.TradeResultModel;

import java.util.Map;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: ChannelProcessor.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  交易渠道处理器
 *  pingxx统一第三方支付渠道（支持支付宝、微信）。
 *  现金、虚拟账户
 * 
 *  Notes:
 *  $Id: ChannelProcessor.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public interface ChannelProcessor {


	/**
	 * 处理交易请求
	 * @param serialModel	交易流水
	 * @param tradeType		交易類型
	 * @param extraParams	附加參數（包含渠道所需附加參數、當前系統附加參數）
	 * @return
	 * @throws Exception
	 */
	TradeResultModel processTradeRequest(TransactionsSerialModel serialModel, TradeType tradeType, Map<String, Object> extraParams) throws Exception;







	/**
	 * 	处理返回结果
	 *	异步返回结果、前端直接返回结果
	 *
	 * @param serialNo 异步交易结果
	 * @param tradeStatus 交易状态
	 * @return
	 * @throws Exception
	 */
	TradeResultModel processTradeResult(String serialNo, TradeStatus tradeStatus, Object result)throws Exception;
}

/*
*$Log: av-env.bat,v $
*/