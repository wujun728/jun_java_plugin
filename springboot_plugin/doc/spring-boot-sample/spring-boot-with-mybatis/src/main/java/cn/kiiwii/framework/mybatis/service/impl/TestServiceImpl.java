package cn.kiiwii.framework.mybatis.service.impl;


import cn.kiiwii.framework.mybatis.mapper.TestMapper;
import cn.kiiwii.framework.mybatis.model.Account;
import cn.kiiwii.framework.mybatis.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhong on 2016/9/5.
 */
@Service("testService")
public class TestServiceImpl implements ITestService {

    Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private TestMapper testMapper;
    public void test() {
    }

    @Transactional(propagation= Propagation.REQUIRED )
    @Override
    public boolean transfer(float money, int from, int to) throws Exception {

        this.testMapper.minusMoney(from, money);
        int i = 1/0;
        this.testMapper.addMoney(to, money);
        return true;
    }

    @Override
    public int insertAccount(Account account){
        return this.testMapper.insertAccount(account);
    }

    @Override
    public Account findAccountById(int i) {

        return this.testMapper.getAccountById(i);
    }

    @Override

    public List<Account> findAccountsById(int i) {
        List<Account> accounts = this.testMapper.findAccountsById(i);
        return this.testMapper.findAccountsById(i);
    }
}
