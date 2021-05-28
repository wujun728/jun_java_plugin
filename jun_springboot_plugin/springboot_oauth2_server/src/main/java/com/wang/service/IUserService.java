package com.wang.service;

import com.wang.domain.UserEntity;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public interface IUserService {

    UserEntity findByname(String username);
}
