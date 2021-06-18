package com.itheima.dao;

public interface AccountDao {
	/**
	 * 转账
	 * @param sourceAccountName 转出账户
	 * @param targetAccontName 转入账户
	 * @param money 交易金额
	 */
	void transfer(String sourceAccountName,String targetAccontName,float money);
}
