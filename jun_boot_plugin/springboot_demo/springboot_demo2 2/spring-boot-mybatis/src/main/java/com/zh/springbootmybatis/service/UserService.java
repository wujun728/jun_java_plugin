package com.zh.springbootmybatis.service;

import com.zh.springbootmybatis.model.User;

import java.util.List;

/**
 * @author Wujun
 * @date 2019/5/31
 */
public interface UserService {

    User findById(Integer id);

    List<User> listByAge(Integer age);

    void save(User user);

    void save(List<User> users);

    void deleteById(Integer id);
}
