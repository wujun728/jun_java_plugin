package org.typroject.tyboot.prototype.trade.channel.virtual.trade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.prototype.account.AccountManager;
import org.typroject.tyboot.prototype.account.AccountType;
import org.typroject.tyboot.prototype.account.trade.DefaultAccountTradeType;
import org.typroject.tyboot.prototype.trade.Trade;
import org.typroject.tyboot.prototype.trade.TradeException;
import org.typroject.tyboot.prototype.trade.TradeResultModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component(value = "virtualPayment")
public class VirtualPayment implements Trade{

	
	private static final Logger logger = LoggerFactory.getLogger(VirtualPayment.class);


	
	@Override
	public TradeResultModel process(TransactionsSerialModel serialModel, Map<String, Object> extra) throws Exception {


		TradeResultModel resultModel = new TradeResultModel();//交易结果

		AccountType[] accountTypes = (AccountType[])extra.get("accountType");

		Map<AccountManager,BigDecimal> accountManagerMap = new HashMap<>();

		BigDecimal amount = serialModel.getTradeAmount();


		//支持多账户消费出账。。按指定顺序进行出账操作
		if(!ValidationUtil.isEmpty(accountTypes))
		{
			for(AccountType accountType : accountTypes)
			{
				AccountManager accountManager = new AccountManager(serialModel.getUserId(),accountType);

				if(accountManager.getAccountModel().getBalance().doubleValue() <= 0)
					throw new TradeException("余额不足，请先去充值.");

				if(accountManager.getAccountModel().getBalance().doubleValue()>0 &&
						accountManager.getAccountModel().getBalance().doubleValue()< serialModel.getTradeAmount().doubleValue())
				{
					accountManagerMap.put(accountManager,accountManager.getAccountModel().getBalance());
					amount = amount.subtract(accountManager.getAccountModel().getBalance());
					continue;
				}

				if(accountManager.getAccountModel().getBalance().doubleValue()>0 &&
						accountManager.getAccountModel().getBalance().doubleValue() >= serialModel.getTradeAmount().doubleValue())
				{
					accountManagerMap.put(accountManager,amount);
					break;
				}
			}
		}

		for(AccountManager accountManager:accountManagerMap.keySet())
		{
			this.excu(serialModel.getBillNo(),accountManagerMap.get(accountManager),accountManager);
		}

		resultModel.setCalledSuccess(true);
		resultModel.setResult(serialModel);
		return resultModel;
	}


	private TradeResultModel excu(String billNo,BigDecimal amount ,AccountManager accountManager) throws Exception
	{
		TradeResultModel resultModel = new TradeResultModel();//交易结果
		Map<String, Object> extra = new HashMap<>();
		extra.put("billNo",billNo);
		extra.put("amount",amount);
		boolean result = accountManager.executeTrade(DefaultAccountTradeType.PAYMENT,extra);
		resultModel.setCalledSuccess(result);
		return resultModel;
	}



	
	

	
	
}
