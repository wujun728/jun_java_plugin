package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.AccountDao;
import com.itheima.domain.Account;
import com.itheima.util.TransactionManager;

public class AccountDaoImpl implements AccountDao {
	private QueryRunner qr = new QueryRunner();
	
	public Account findByName(String accountName) {
		try {
			return qr.query(TransactionManager.getConnection(),"select * from account where name=?", new BeanHandler<Account>(Account.class),accountName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateAcount(Account account) {
		try {
			qr.update(TransactionManager.getConnection(),"update account set money=? where id=?", account.getMoney(),account.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
