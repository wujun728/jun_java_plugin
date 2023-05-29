package com.zhu.webservice.service;

import org.springframework.stereotype.Service;

import com.zhu.webservice.bean.UserBean;

@Service
public interface ILoginService {

	public abstract UserBean searchUser(UserBean user);

}