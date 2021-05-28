package org.typroject.tyboot.prototype.account.trade.impl;

import org.springframework.stereotype.Component;
import org.typroject.tyboot.prototype.account.Account;
import org.typroject.tyboot.prototype.account.DefaultAccountType;
import org.typroject.tyboot.prototype.account.trade.AccountTradeHandler;
import org.typroject.tyboot.prototype.account.trade.BaseTradeParams;
import org.typroject.tyboot.prototype.account.trade.DefaultAccountTradeType;
import org.typroject.tyboot.prototype.account.trade.TradeParams;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.Map;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: FreezeHandler.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  凍結指定賬戶金額
 * 
 *  Notes:
 *  $Id: FreezeHandler.java  Tyrest\magintrursh $
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
@Component(value = "freezeHandler")
public class FreezeHandler  implements AccountTradeHandler {



	/**交易参数*/
	public enum FreezeParams implements TradeParams {
 
		billNo(true,"账单号"),	   //用户賬單
		userId(true,"用戶Id"),	   //交易金额
		amount(true,"交易金额"),	   //交易金额
		transferType(false,"轉賬類型"),//可能有多種業務需要凍結資金，轉賬類型作爲前置業務標識
		postscript(true,"交易附言"); //附言
		
		private boolean notnull;
		private String paramName;
		FreezeParams(boolean notnull,String paramName)
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
		 if(BaseTradeParams.checkPrams(params, FreezeParams.values()))
		 {
			 //獲取當前凍結賬戶信息
			 String  userId 				= (String)params.get(FreezeParams.userId.name());
			 Account targetAccount  	= Account.getAccountInstance( userId, DefaultAccountType.FROZEN);
			 String transferType		= (String)params.get(FreezeParams.transferType.name());

			 params.put("transferType", ValidationUtil.isEmpty(transferType)? DefaultAccountTradeType.FREEZE.name():transferType);
			 params.put("targetAccountNo",targetAccount.getAccountInfoModel().getAccountNo());


			 //將衹當金額從來源賬戶轉入凍結賬戶
			 AccountTradeHandler transferHandler
					 					= (AccountTradeHandler)SpringContextHelper.getBean("transferHandler");
			 flage 						=  transferHandler.execute(params,account);
		 }
		return flage;	
	}
	
	

}