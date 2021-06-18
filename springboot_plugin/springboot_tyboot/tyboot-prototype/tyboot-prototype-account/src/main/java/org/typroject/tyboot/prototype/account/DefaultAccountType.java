package org.typroject.tyboot.prototype.account;

public enum DefaultAccountType  implements AccountType {

	

	/**
	 * 虚拟账户,默认账户
	 */
	VIRTUAL("虚拟账户",1),

	PRESRENT("赠送资金",2),
	
	/**
	 * 冻结账户；用作资金暂存，转移等。
	 */
	FROZEN("冻结账户",3),
	
	/**
	 * 积分账户
	 */
	SCORE("积分账户",4);
	
	private String accountName;
	private int accountNoSuffix;
	
	 DefaultAccountType(String accountName, int accountNoSuffix)
	{
		this.accountName = accountName;
		this.accountNoSuffix = accountNoSuffix;
	}

	@Override
	public String getAccountName()
	{
		return accountName;
	}

	@Override
	public int getAccountNoSuffix() {
		return accountNoSuffix;
	}

	/**
	 *
	 * 账户类型标识code
	 *
	 * @return
	 */
	public String getAccountType()
	{
		return this.name();
	}
}
