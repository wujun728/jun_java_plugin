package com.circle.service;


import com.circle.dao.IUserDao;
import com.circle.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService { // Service接口实现

    // DAO对象
    @Autowired
    private IUserDao userDao;

    // userDao的get方法
    public IUserDao getUserDao() {
        return userDao;
    }

    // userDao的set方法
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    //返回记录总数
    public int getUsersCount() {
        //调用DAO
        return userDao.getUsersCount();
    }
    //返回所有的User
    public List<User> listUsers() {
        //调用DAO
        return userDao.listUsers();
    }

    // 注册
    @Transactional( readOnly = false)
    public boolean register(User user) {
        //检查同名用户是否存在
        if(userDao.findUserByName(user.getName())!= null)
            //抛出异常
            throw new RuntimeException("用户"+user.getName()+"已经存在");
        //如果不存在则创建
        boolean flag = false;
        try {
            userDao.register(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 登录
    public boolean login(String name, String password) {
        int result = userDao.login(name, password);

        if (1 == result) {
            return true;
        } else {
            return false;
        }
    }

    // 删除
    @Transactional( readOnly = false)
    public boolean delete(User user) {
        boolean flag = false;
        try {
            userDao.delete(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 修改
    @Transactional( readOnly = false)
    public boolean update(User user) {
        boolean flag = false;
        try {
            userDao.update(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 添加
    @Transactional( readOnly = false)
    public boolean add(User user) {
        boolean flag = false;
        try {
            userDao.add(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}

