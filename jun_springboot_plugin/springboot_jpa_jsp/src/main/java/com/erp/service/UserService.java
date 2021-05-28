package com.erp.service;

import com.erp.entity.Oauth2User;
import com.erp.exception.MyException;

/**
 * @Author Administrator
 * @CreateDate 2018/4/17 10:18
 */
public interface UserService {

    Oauth2User findOauth2UsersByName(String username);
    void  saveUser(Oauth2User u) throws Exception;



}
