package com.zy.service;

import com.zy.entity.Info;
import com.zy.entity.User;

import java.util.List;

/**
 * <p></p>
 * Created by zhezhiyong@163.com on 2017/9/21.
 */
public interface UserService {

    List<User> list();

    User findUserById(Long id);

    Info findInfoById(Long id);

    User update(User user);

    void remove(Long id);

}
