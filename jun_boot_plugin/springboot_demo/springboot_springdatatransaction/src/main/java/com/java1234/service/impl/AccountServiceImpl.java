package com.java1234.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.java1234.dao.AccountDao;
import com.java1234.entity.Account;
import com.java1234.service.AccountService;

/**
 * 帐号Service实现类
 * @author Wujun
 *
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService{

	@Resource
	private AccountDao accountDao;
	
	@Transactional
	public void transferAccounts(int fromUser, int toUser, float account) {
		Account fromAccount=accountDao.getOne(fromUser);
		fromAccount.setBalance(fromAccount.getBalance()-account);
		accountDao.save(fromAccount);
		
		Account toAccount=accountDao.getOne(toUser);
		toAccount.setBalance(toAccount.getBalance()+account);
//		int zero=1/0;
		accountDao.save(toAccount);
	}

}
