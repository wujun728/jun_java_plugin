package org.typroject.tyboot.prototype.trade.channel.virtual;

import org.springframework.stereotype.Component;
import org.typroject.tyboot.prototype.trade.channel.BaseChannelProcess;
import org.typroject.tyboot.prototype.trade.channel.ChannelProcessor;
import org.typroject.tyboot.prototype.trade.Trade;
import org.typroject.tyboot.prototype.trade.TradeResultModel;
import org.typroject.tyboot.prototype.trade.TradeStatus;
import org.typroject.tyboot.prototype.trade.TradeType;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;

import java.util.Map;

@Component(value = "virtualChannel")
public class VirtualChannelImpl extends BaseChannelProcess implements ChannelProcessor {



    private static final String CHANNEL_PIX = "virtual";
    /**
     * 处理交易请求
     * @param serialModel	交易流水
     * @param tradeType		交易類型
     * @param extraParams	附加參數（包含渠道所需附加參數、當前系統附加參數）
     * @return
     * @throws Exception
     */
   public TradeResultModel processTradeRequest(TransactionsSerialModel serialModel, TradeType tradeType, Map<String, Object> extraParams) throws Exception
   {
       Trade trade = (Trade)SpringContextHelper.getBean(CHANNEL_PIX+tradeType.getTradeProcessor());
       TradeResultModel resultModel =  trade.process(serialModel, extraParams);
       return resultModel;
   }







    /**
     * 	处理返回结果
     *	异步返回结果、前端直接返回结果
     *
     * @param serialNo 异步交易结果
     * @param tradeStatus 交易状态
     * @return
     * @throws Exception
     */
    public TradeResultModel processTradeResult(String serialNo, TradeStatus tradeStatus, Object result)throws Exception
    {
        TradeResultModel resultModel = new TradeResultModel();
        resultModel.setCalledSuccess(true);
        return resultModel;
    }

}
