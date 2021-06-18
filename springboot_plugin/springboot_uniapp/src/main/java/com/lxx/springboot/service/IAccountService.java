package com.lxx.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxx.springboot.meta.po.Account;
import java.util.List;

public interface IAccountService extends IService<Account> {

    /*int add(Account account);

    int update(Account account);

    int delete(int id);*/

    Account findAccountById(int id);

//    List<Account> findAccountList();

}
