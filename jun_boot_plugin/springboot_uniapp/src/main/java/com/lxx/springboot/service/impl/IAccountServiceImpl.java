package com.lxx.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxx.springboot.dao.AccountMapper;
import com.lxx.springboot.meta.po.Account;
import com.lxx.springboot.service.IAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

/*    @Override
    public int add(Account account) {
        return 0;
    }

    @Override
    public int update(Account account) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }*/

    @Override
    public Account findAccountById(int id) {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("id", id);
        Account account= this.getOne(accountQueryWrapper);
        return account;
    }

    /*@Override
    public List<Account> findAccountList() {
        return null;
    }*/
}
