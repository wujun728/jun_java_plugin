package com.zh.springbootshiro.service;

import com.zh.springbootshiro.model.User;

/**
 * @author Wujun
 * @date 2019/6/25
 */
public interface UserService {

    User findByUserName(String userName);

}
