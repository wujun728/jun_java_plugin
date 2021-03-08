package com.zh.springbootjwt.service;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootjwt.model.User;

/**
 * @author Wujun
 * @date 2019/6/25
 */
public interface UserService {

    User findByUserId(Integer userId);

    JSONObject login(String username,String password);
}
