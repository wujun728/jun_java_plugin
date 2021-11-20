package com.jun.mvc.dao;

import com.jun.mvc.BaseTests;
import com.jun.mvc.dao.IAccountDao;
import com.jun.mvc.entity.Account;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountDaoTest extends BaseTests {

    @Autowired
    private IAccountDao accountDao;

    @Test
    public void testFindAll() {
        List<Account> accounts = accountDao.findAll();
        //
        if(accounts != null) {
            accounts.forEach(System.out::println);
        }
    }
}
