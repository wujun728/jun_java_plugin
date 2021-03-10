package org.typroject.tyboot.prototype.account.trade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.prototype.account.Account;
import org.typroject.tyboot.prototype.account.DefaultAccountType;
import org.typroject.tyboot.prototype.account.trade.AccountTradeHandler;
import org.typroject.tyboot.prototype.account.trade.BaseTradeParams;
import org.typroject.tyboot.prototype.account.trade.DefaultAccountTradeType;
import org.typroject.tyboot.prototype.account.trade.TradeParams;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.face.account.model.AccountTransferRecordModel;
import org.typroject.tyboot.face.account.service.AccountTransferRecordService;

import java.util.Map;

@Component(value = "unfreezeHandler")
public class UnfreezeHandler    implements AccountTradeHandler {


	@Autowired
	private AccountTransferRecordService accountTransferRecordService;
	/**交易参数*/
	private enum UnfreezeParams implements TradeParams {
 
		billNo(true,"账单号"),	   //用户賬單
		userId(true,"用户id"),
		transferType(false,"轉賬類型"),//可能有多種業務需要凍結資金，轉賬類型作爲前置業務標識
		postscript(true,"交易附言"); //附言
		
		private boolean notnull;
		private String paramName;
		UnfreezeParams(boolean notnull,String paramName)
		{
			this.notnull = notnull;
			this.paramName = paramName; 
		}  
		public boolean isNotnull()
		{
			return notnull;
		}
		public String getParesStr()
		{
			return paramName;
		}
		public String getParamCode(){return this.name();}
		
	}

	
	
	@Override
	public boolean execute(Map<String, Object> params,Account account) throws Exception {
		boolean flage = false;
		 if(BaseTradeParams.checkPrams(params, UnfreezeParams.values()))
		 {
			 String billNo 				= (String)params.get(UnfreezeParams.billNo.name());
			 String    userId 			=(String)params.get(UnfreezeParams.userId.name());
			 String transferType		= (String)params.get(UnfreezeParams.transferType.name());

			 //获取冻结记录
			 AccountTransferRecordModel transferRecord
					 					=  this.accountTransferRecordService.queryRecord(userId,account.getAccountInfoModel().getAccountNo(),billNo,transferType);

			 params.put("transferType", DefaultAccountTradeType.UNFREEZE.name());
			 params.put("targetAccountNo",transferRecord.getSourceAccountNo());
			 params.put("amount",transferRecord.getTransferAmount());

			 //当前用户的冻结账户
			 Account  freezeAccount 	= Account.getAccountInstance(userId, DefaultAccountType.FROZEN);
			 //将冻结的资金转回原来账户
			 AccountTradeHandler transferHandler
					 					= (AccountTradeHandler)SpringContextHelper.getBean("transferHandler");
			 flage 						=  transferHandler.execute(params,freezeAccount);
		 }
		return flage;	
	}
	
	

}