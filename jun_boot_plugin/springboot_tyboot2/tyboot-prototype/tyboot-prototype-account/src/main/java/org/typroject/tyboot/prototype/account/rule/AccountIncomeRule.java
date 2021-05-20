package org.typroject.tyboot.prototype.account.rule;

import org.springframework.stereotype.Component;
import org.typroject.tyboot.prototype.account.Account;
import org.typroject.tyboot.prototype.account.AccountOperateRule;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: AccountIncomeRule.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  账户入账限制条件
 * 
 *  Notes:
 *  $Id: AccountIncomeRule.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
@Component(value = "accountIncomeRule")
public class AccountIncomeRule implements AccountOperateRule
{

	@Override
	public boolean checkOperation(Account account )
	{
		return false;
	}
	
}
