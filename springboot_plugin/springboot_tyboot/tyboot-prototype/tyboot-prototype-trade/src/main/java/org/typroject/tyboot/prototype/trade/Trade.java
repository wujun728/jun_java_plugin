package org.typroject.tyboot.prototype.trade;

import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;

import java.util.Map;

public interface Trade {


	/**
	 * 處理交易
	 * @param serialModel
	 * @param extra
	 * @return
	 * @throws Exception
	 */
	TradeResultModel process(TransactionsSerialModel serialModel, Map<String, Object> extra)throws Exception ;


}
