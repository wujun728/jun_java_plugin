package com.itheima.dao;

import com.itheima.domain.Account;

//DAO层：不能牵扯到任何业务有关的逻辑。
//DAO:只负责CRUD
public interface AccountDao {
	/**
	 * 根据户名查询账户
	 * @param accountName
	 * @return
	 */
	Account findByName(String accountName);
	/**
	 * 更新账户
	 * @param account
	 */
	void updateAcount(Account account);
}
