package cn.kiiwii.framework.dao;

import cn.kiiwii.framework.controller.User;

import java.util.List;

/**
 * Created by zhong on 2016/9/19.
 */
public interface IUserRedisDAO {
    Object get(String name);

    void set(String name, String s);

    void set(String name, User user);

    void set(String name, List<User> users);
}
