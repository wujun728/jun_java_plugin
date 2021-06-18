package com.dubbos.service;

import java.util.List;
import com.dubbos.entity.User;

/**
 * Created by qilong on 2016-04-09-0009.
 */
public interface UserService {
    public List<User> findAllUser();
    public void saveUser(User user);
    public User getUserById(Integer userId);
    public void deleteById(Integer userId);
}
