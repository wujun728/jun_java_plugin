package com.jun.plugin.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.plugin.springboot.meta.po.Account;

import java.util.List;

public interface IAccountService extends IService<Account> {

    /*int add(Account account);

    int update(Account account);

    int delete(int id);*/

    Account findAccountById(int id);

//    List<Account> findAccountList();

}
