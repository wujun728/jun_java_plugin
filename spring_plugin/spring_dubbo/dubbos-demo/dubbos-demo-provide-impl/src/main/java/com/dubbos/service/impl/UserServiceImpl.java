package com.dubbos.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbos.entity.User;
import com.dubbos.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qilong on 2016-04-09-0009.
 */
@Service(interfaceClass = UserService.class, version = "0.0.1", protocol = {"dubbo"})
public class UserServiceImpl implements UserService {
    public List<User> findAllUser() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            User user = new User();
            user.setId(i);
            user.setUsername("用户" + i);
            user.setPassword("password" + i);
            user.setGender(i % 2 == 0 ? 1 : 2);
            user.setCreatetime(new Date());
            user.setUpdatetime(new Date());
            users.add(user);
        }
        return users;
    }

    public void saveUser(User user) {

    }

    public User getUserById(Integer userId) {
        return null;
    }

    public void deleteById(Integer userId) {

    }
}
