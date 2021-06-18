package com.itheima.service.impl;


import com.itheima.dao.AccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.domain.Account;
import com.itheima.service.BusinessService;
import com.itheima.util.TransactionManager;

//业务层控制事务
public class BusinessServiceImpl implements BusinessService {
	private AccountDao dao = new AccountDaoImpl();
	public void transfer(String sourceAccountName, String targetAccontName,
			float money) {
		try {
			TransactionManager.startTransaction();
			Account sAccount = dao.findByName(sourceAccountName);
			Account tAccount = dao.findByName(targetAccontName);
			sAccount.setMoney(sAccount.getMoney() - money);
			tAccount.setMoney(tAccount.getMoney() + money);
			dao.updateAcount(sAccount);
			 int i=1/0;
			dao.updateAcount(tAccount);
		} catch (Exception e) {
			TransactionManager.rollback();
			e.printStackTrace();
		} finally {
			TransactionManager.commit();
			TransactionManager.release();
		}
	}

}
