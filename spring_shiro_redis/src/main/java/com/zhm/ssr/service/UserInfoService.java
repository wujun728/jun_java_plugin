package com.zhm.ssr.service;

import com.zhm.ssr.model.UserInfo;

import java.util.List;

/**
 * Created by zhm on 2015/7/10.
 */
public interface UserInfoService {
    UserInfo findByUserid(String userid);


    long findNumsByCond(String search);

    List<UserInfo> findByCond(String search, String order, int offset, int limit);

    void delInfoById(int id);
}
