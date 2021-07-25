package com.jun.plugin.springwithmybatis.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jun.plugin.springwithmybatis.model.Account;
import com.jun.plugin.springwithmybatis.service.ITestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhong on 2016/3/20.
 */
@Controller
@RequestMapping("/")
public class TestController {
    private Logger logger = Logger.getLogger(TestController.class);
    @Autowired
    private ITestService testService;

    @RequestMapping("/test")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<Account> accountList = this.testService.findAccountsById(3);

        logger.info(accountList);
    }
}
