package com.lxx.springboot.controller;

import com.lxx.springboot.dao.redis.RedisDao;
import com.lxx.springboot.meta.po.Account;
import com.lxx.springboot.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 账户 类
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccountService accountService;
    @Autowired
    RedisDao redisDao;

    /**
     * Postman 工具 请求 http://127.0.0.1:8880/my/account/1
     * 结果：
     *    {
     *     "id": 1,
     *     "name": "张三",
     *     "money": 1000
     *    }
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Account getAccountById(@PathVariable("id") int id){
        Account account = accountService.findAccountById(id);
        return account;
    }
}
