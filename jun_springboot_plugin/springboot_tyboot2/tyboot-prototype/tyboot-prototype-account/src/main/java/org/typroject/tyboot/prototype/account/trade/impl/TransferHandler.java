package org.typroject.tyboot.prototype.account.trade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.prototype.account.Account;
import org.typroject.tyboot.prototype.account.trade.AccountTradeHandler;
import org.typroject.tyboot.prototype.account.trade.BaseTradeParams;
import org.typroject.tyboot.prototype.account.trade.DefaultAccountTradeType;
import org.typroject.tyboot.prototype.account.trade.TradeParams;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.account.model.AccountTransferRecordModel;
import org.typroject.tyboot.face.account.service.AccountTransferRecordService;

import java.math.BigDecimal;
import java.util.Map;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: PaymentHandler.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  虛擬賬戶轉賬
 * 
 *  Notes:
 *  $Id: PaymentHandler.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
@Component(value = "transferHandler")
public class TransferHandler    implements AccountTradeHandler {


	@Autowired
	AccountTransferRecordService accountTransferRecordService;

	/**交易参数*/
	private enum PaymentParams implements TradeParams {

		billNo(true,"账单号"),
		transferType(true,"轉賬類型"),//轉賬的前置業務標識
		amount(true,"交易金额"),
		postscript(true,"交易附言"),
		targetAccountNo(true,"目标账户编号");
		
		private boolean notnull;
		private String paramName;
		PaymentParams(boolean notnull,String paramName)
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
		//解析参数
		 if(BaseTradeParams.checkPrams(params, PaymentParams.values()))
		 {
			BigDecimal amount 		   = (BigDecimal)params.get(PaymentParams.amount.name());
			String targetAccountNo = (String)params.get(PaymentParams.targetAccountNo.name());
			String billNo		   = (String)params.get(PaymentParams.billNo.name());
			String postscript	   = (String)params.get(PaymentParams.postscript.name());
			String transferType    = (String)params.get(PaymentParams.transferType.name());
			Account targetAccount  = Account.getAccountInstance(targetAccountNo);


			 //从來源账户出账
			boolean  spendResult   = account.spend(amount, DefaultAccountTradeType.TRANSFER_INTERNAL,billNo);
			 //为目标账户入账
			boolean incomeResult   = targetAccount.income(amount,DefaultAccountTradeType.TRANSFER_INTERNAL,billNo);
			               flage   = spendResult && incomeResult;
			if(!spendResult || !incomeResult)
			{
				throw new Exception("转账出现异常.");
			}

			//保存转账记录
			 AccountTransferRecordModel newRecord
					               = accountTransferRecordService.saveTransferRecord(  account.getAccountInfoModel().getUserId(),
																		 billNo,  account.getAccountInfoModel().getAccountNo(),  account.getAccountInfoModel().getAccountType(),
																		  targetAccountNo,  targetAccount.getAccountInfoModel().getAccountType(), amount, postscript,  transferType);
			 flage 				   = flage && !ValidationUtil.isEmpty(newRecord);
		 }	 
		return flage;	
	}
	
	

}