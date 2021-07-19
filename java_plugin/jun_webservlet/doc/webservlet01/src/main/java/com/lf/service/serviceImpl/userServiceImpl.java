package com.lf.service.serviceImpl;

import com.lf.Dao.Dao;
import com.lf.Dao.Impl.DaoImpl;
import com.lf.bean.userBean;
import com.lf.service.userService1;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: userServiceImpl
 * @Description:
 * @Author: 李峰
 * @Date: 2021 年 01月 18 18:12
 * @Version 1.0
 */
public class userServiceImpl implements userService1 {

    Dao dao=new DaoImpl();
    @Override

    public int addUserService(userBean user) throws Exception {
        return dao.addMember(user);
    }
    @Override
    public List<userBean> selectAll() throws Exception {
        return dao.selectAllDao();
    }
}
