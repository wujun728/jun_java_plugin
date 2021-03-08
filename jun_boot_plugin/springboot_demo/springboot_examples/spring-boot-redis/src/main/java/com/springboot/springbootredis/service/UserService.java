package com.springboot.springbootredis.service;

import com.springboot.springbootredis.model.User;

/**
 * @Version: 1.0
 * @Desc:
 */
public interface UserService {
    User save(User user);

    User get(Long id);

    void delete(Long id);
}
