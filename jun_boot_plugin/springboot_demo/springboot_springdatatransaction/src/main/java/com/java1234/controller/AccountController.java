package com.java1234.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java1234.service.AccountService;

/**
 * 账户controller层
 * @author Wujun
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	@Resource
	private AccountService accoutService;
	
	@RequestMapping("/transfer")
	public String transferAccount(){
		try{
			accoutService.transferAccounts(1, 2, 200);		
			return "OK";
		}catch(Exception e){
			return "NO";
		}
	}
}
