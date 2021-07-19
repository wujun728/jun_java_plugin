package cn.kiiwii.framework.mybatis.service;

import cn.kiiwii.framework.mybatis.model.Account;

import java.util.List;

/**
 * Created by zhong on 2016/9/5.
 */
public interface ITestService {

    public void test();

    public boolean transfer(float money, int from, int to) throws Exception;

    public int insertAccount(Account account) throws Exception;

    public Account findAccountById(int i);

    public List<Account> findAccountsById(int i);
}
