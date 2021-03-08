package com.zh.springbootjpa.service;

import com.zh.springbootjpa.model.User;

import java.util.List;

/**
 * @author Wujun
 * @date 2019/5/31
 */
public interface UserService {

    User findById(Integer id);

    User findByName(String name);

    List<User> listByAge(Integer age);

    void save(User user);

    void save(List<User> users);

    void updateAgeByName(String name,Integer age);

    void deleteById(Integer id);
}
