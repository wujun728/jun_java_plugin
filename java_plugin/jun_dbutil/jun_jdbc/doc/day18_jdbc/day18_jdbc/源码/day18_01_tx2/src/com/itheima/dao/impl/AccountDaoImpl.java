package com.itheima.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.AccountDao;
import com.itheima.domain.Account;

public class AccountDaoImpl implements AccountDao {
	private QueryRunner qr = new QueryRunner();
	private Connection conn;
	public AccountDaoImpl(Connection conn){
		this.conn  = conn;
	}
	public Account findByName(String accountName) {
		try {
			return qr.query(conn,"select * from account where name=?", new BeanHandler<Account>(Account.class),accountName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateAcount(Account account) {
		try {
			qr.update(conn,"update account set money=? where id=?", account.getMoney(),account.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
