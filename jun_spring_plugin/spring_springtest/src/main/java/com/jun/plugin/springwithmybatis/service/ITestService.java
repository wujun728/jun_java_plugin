package com.jun.plugin.springwithmybatis.service;

import java.util.List;

import com.jun.plugin.springwithmybatis.model.Account;
import com.jun.plugin.springwithmybatis.model.User;

public interface ITestService {

	
	public void test();
	
	public boolean transfer(float money, int from, int to) throws Exception;
	
	public int insertAccount(Account account) throws Exception;

	public Account findAccountById(int i);
	
	public User findUserById(int i);

	public List<Account> findAccountsById(int i);
}
