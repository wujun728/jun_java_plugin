package com.itheima.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.itheima.dao.AccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.domain.Account;
import com.itheima.service.BusinessService;
import com.itheima.util.DBCPUtil;

//业务层控制事务
public class BusinessServiceImpl implements BusinessService {
	public void transfer(String sourceAccountName, String targetAccontName,
			float money) {
		Connection conn = null;
		try {
			conn = DBCPUtil.getConnection();
			conn.setAutoCommit(false);
			AccountDao dao = new AccountDaoImpl(conn);
			Account sAccount = dao.findByName(sourceAccountName);
			Account tAccount = dao.findByName(targetAccontName);
			sAccount.setMoney(sAccount.getMoney() - money);
			tAccount.setMoney(tAccount.getMoney() + money);
			dao.updateAcount(sAccount);
			// int i=1/0;
			dao.updateAcount(tAccount);
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.commit();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
