package com.jun.plugin.ssr.service;

import java.util.List;

import com.jun.plugin.ssr.model.UserInfo;

/**
 * Created by zhm on 2015/7/10.
 */
public interface UserInfoService {
    UserInfo findByUserid(String userid);


    long findNumsByCond(String search);

    List<UserInfo> findByCond(String search, String order, int offset, int limit);

    void delInfoById(int id);
}
