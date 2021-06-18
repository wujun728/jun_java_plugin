package com.zhm.ssr.dao;

import com.zhm.ssr.model.UserInfo;

import java.util.List;

/**
 * Created by zhm on 2015/7/10.
 */
public interface UserInfoDao {
    UserInfo findByUserid(String userid);

    long findNumsByCond(String keyword);

    List<UserInfo> findByCond(String keyword, String order, int offset, int limit);

    void delInfoById(int id);
}
