package org.typroject.tyboot.prototype.account;

import org.typroject.tyboot.prototype.account.trade.AccountTradeHandler;
import org.typroject.tyboot.prototype.account.trade.AccountTradeType;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.account.model.AccountInfoModel;

import java.util.Map;

public final class AccountManager {
	
	private Account account;
	
	/**
	 * 根据userId和accountType创建一个AccountManager操作对象
	 * @param userId
	 * @param accountType
	 * @throws Exception
	 */
	public AccountManager(String  userId,AccountType accountType) throws Exception {
		this.account  = Account.getAccountInstance(userId,accountType);
	}
	
	/**
	 * 根据accountNo创建一个AccountManager操作对象
	 * @param accountNo
	 * @throws Exception
	 */
	public AccountManager(String accountNo) throws Exception {
		this.account  = Account.getAccountInstance(accountNo);
	}
	

	/**
	 * 
	 * 执行交易
	 * @param accountTradeType	交易类型
	 * @param params	交易参数
	 * @throws Exception
	 */
	public boolean executeTrade(AccountTradeType accountTradeType, Map<String,Object> params) throws Exception
	{
		//#1.验证操作规则
		//#2.初始化账户操作对象
		//#3.执行交易
		//#4.交易结果处理
		AccountTradeHandler tradeHandler = (AccountTradeHandler)SpringContextHelper.getBean(accountTradeType.getAccountTradeHandler());
		if(ValidationUtil.isEmpty(tradeHandler)){
			throw new Exception("账户交易处理器未找到");
		}
		return tradeHandler.execute(params,account);
	}
	
	
	
	
	

	public static boolean initAccountInfo(String userId,AccountType accountType) throws Exception
	{
		Account.initAccountInfo(userId,accountType);
		return true;
	}
	

	
	
	/**
	 * 锁定账户
	 * @param userId		
	 * @param accountType
	 * @return
	 * @throws Exception 
	 */
	public boolean lock(Long userId, AccountType accountType) throws Exception
	{
		return account.lock();
	}


	public AccountInfoModel getAccountModel() throws Exception {
			return Bean.copyExistPropertis(this.account.getAccountInfoModel(),new AccountInfoModel());
	}

	public Account getAccountInstance()
	{
		return this.account;
	}

}
