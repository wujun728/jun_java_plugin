package com.itheima.service.impl;

import com.itheima.dao.AccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
	private AccountDao dao = new AccountDaoImpl();
	public void transfer(String sourceAccountName, String targetAccontName,
			float money) {
		dao.transfer(sourceAccountName, targetAccontName, money);
	}

}
