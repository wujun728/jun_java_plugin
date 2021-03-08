package com.springboot.springbootswagger.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.springbootswagger.model.User;
import com.springboot.springbootswagger.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Version: 1.0
 * @Desc:
 */
@Service
public class UserServiceImpl implements UserService {

    private static Map<Long, User> USER_MAP = Maps.newHashMap();

    static {
        USER_MAP.put(1L, new User(1L, "geekdigging", new Date(), 18));
        USER_MAP.put(2L, new User(2L, "极客挖掘机", new Date(), 28));
    }

    @Override
    public User getUserById(Long id) {
        return USER_MAP.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        return Lists.newArrayList(USER_MAP.values().iterator());
    }

    @Override
    public User saveUser(User user) {
        USER_MAP.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        USER_MAP.remove(id);
    }
}
