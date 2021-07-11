package com.lf.service;

import com.lf.bean.userBean;

import java.util.List;

public interface userService1 {
    int addUserService(userBean user) throws Exception;
    List<userBean> selectAll() throws Exception;
}
