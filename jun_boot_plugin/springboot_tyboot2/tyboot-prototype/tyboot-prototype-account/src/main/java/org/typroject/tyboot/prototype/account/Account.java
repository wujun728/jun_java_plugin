package org.typroject.tyboot.prototype.account;


import org.typroject.tyboot.prototype.account.trade.AccountTradeType;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.account.model.AccountInfoModel;
import org.typroject.tyboot.face.account.model.AccountSerialModel;
import org.typroject.tyboot.face.account.service.AccountInfoService;
import org.typroject.tyboot.face.account.service.AccountSerialService;

import java.math.BigDecimal;


/** 
 * 
 * <pre>
 *  Tyrest
 *  File: Account.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  虚拟账户操作类
 *  定义虚拟账户所有的操作
 *  所有方法不可在子类重写 
 * 
 *  Notes:
 *  $Id: Account.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
  public class Account {
	
	

	private static AccountInfoService accountInfoService;
	private static AccountSerialService accountSerialService;

	static {
		accountInfoService 		= (AccountInfoService) SpringContextHelper.getBean(AccountInfoService.class);
		accountSerialService 	= (AccountSerialService) SpringContextHelper.getBean(AccountSerialService.class);
	}


	private Account()
	{

	}
	
	/**
	 * 账户对象
	 */
	private AccountInfoModel accountInfoModel;
	
	
	/**
	 * 初始化账户
	 * @throws Exception 
	 */
	protected static  final AccountInfoModel initAccountInfo(String  userId, AccountType accountType) throws Exception
	{
		return accountInfoService.initAccountInfo(userId,accountType,createAccountNo(userId,accountType));
	}
	
	/**
	 * 入账
	 * @param amount
	 * @return
	 * @throws Exception 
	 */
	public  final   boolean income(BigDecimal amount, AccountTradeType accountTradeType, String billNo) throws Exception
	{
		return this.bookkeeping(amount, accountTradeType, billNo, AccountBaseOperation.INCOME);
	}
	
	
	
	/**
	 * 出账
	 * @param amount
	 * @return
	 * @throws Exception 
	 */
	public final  boolean spend(BigDecimal amount,AccountTradeType accountTradeType,String billNo) throws Exception
	{	
		return this.bookkeeping(amount, accountTradeType, billNo, AccountBaseOperation.SPEND);
	}
	
	
	
	
	
	
	/**
	 * 锁定账户
	 * 锁定状态的账户不能进行任何交易，
	 * @return
	 * @throws Exception
	 */
	protected final  boolean lock() throws Exception
	{		
		return updateAccountStatus(AccountStatus.LOCKED, AccountStatus.NORMAL); 
	}
	
	
	
	/**
	 * 解锁账户
	 * 将账户从锁定状态变为正常状态
	 * @return
	 * @throws Exception
	 */
	protected final  boolean unlock() throws Exception
	{
		return updateAccountStatus(AccountStatus.NORMAL, AccountStatus.LOCKED); 
	}
	
	
	/**
	 * 失效，失效之后的账户不能在进行任何操作，也不可以再恢复到正常状态
	 * @return
	 * @throws Exception 
	 */
	protected final  boolean invalid() throws Exception
	{
		return updateAccountStatus(AccountStatus.INVALID, null); 
	}



	
	
	
	
	/**
	 * 记账
	 * @param amount	金额
	 * @param accountTradeType  账户操作类型
	 * @param billNo			账单编号
	 * @param bookkeeping		记账类型
	 * @return
	 * @throws Exception
	 */
	private final boolean bookkeeping(BigDecimal amount,AccountTradeType accountTradeType,String billNo,AccountBaseOperation bookkeeping ) throws Exception
	{
		boolean returnFlag  = false;
		
		//#1.记录出账流水
		AccountSerialModel newAccountSerial = accountSerialService.createAccountSerial( this.accountInfoModel.getUserId(), this.accountInfoModel.getAccountNo(),this.accountInfoModel.getAccountType(),this.accountInfoModel.getUpdateVersion(), billNo, amount, accountTradeType, bookkeeping);
		
		//#2.变更账户余额
		AccountInfoModel updateResult = accountInfoService.updateFinalBalance(this.accountInfoModel.getAccountNo(),newAccountSerial.getChangeAmount(), this.accountInfoModel.getUpdateVersion(),bookkeeping);
		
		if(!ValidationUtil.isEmpty(updateResult))
		{
			returnFlag = true;
			//#3.刷新账户信息
			this.refresh(this.accountInfoModel.getAccountNo());
		}
		return returnFlag;
	}
	
	
	
	/**
	 * 变更账户状态
	 * @param newStatus
	 * @param oldStatus
	 * @return
	 * @throws Exception 
	 */
	private final boolean updateAccountStatus(AccountStatus newStatus,AccountStatus oldStatus) throws Exception
	{
		boolean returnFlag = false;
		AccountInfoModel updateResult = accountInfoService.updateAccountStatus(accountInfoModel.getAccountNo(), newStatus, oldStatus, this.accountInfoModel.getUpdateVersion());
		
		if(!ValidationUtil.isEmpty(updateResult))
		{
			returnFlag = true;
			this.refresh(this.accountInfoModel.getAccountNo());
		}
		return returnFlag;
	}
	
	

	public final AccountInfoModel getAccountInfoModel() {
		return accountInfoModel;
	}



	private void refresh(String accountNo) throws Exception {

		this.accountInfoModel  = accountInfoService.queryByAccontNo(accountNo);
	}
	
	private static   final  Account newInstance(AccountInfoModel accountInfoModel)
	{
		Account account 			= new Account();
		account.accountInfoModel 	= accountInfoModel;
		return account;
	}



	
	protected final Account setAccountInfo(AccountInfoModel accountInfoModel) throws Exception
	{
		return newInstance(accountInfoModel);
	}


	public static final Account getAccountInstance(String  userId,AccountType accountType) throws Exception
	{
		AccountInfoModel accountInfoModel = accountInfoService.queryByUserId(userId, accountType);
		if(ValidationUtil.isEmpty(accountInfoModel))
			accountInfoModel = initAccountInfo(userId,accountType);
		return newInstance(accountInfoModel);
	}

	public static  final Account getAccountInstance(String accountNo) throws Exception
	{
		return newInstance(accountInfoService.queryByAccontNo(accountNo));
	}


	private static String createAccountNo(String userId,AccountType accountType) throws Exception {
		return userId + accountType.getAccountNoSuffix();
	}

	
}
