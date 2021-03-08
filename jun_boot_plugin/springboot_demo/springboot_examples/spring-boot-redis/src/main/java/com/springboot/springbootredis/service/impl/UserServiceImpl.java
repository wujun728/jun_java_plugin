package com.springboot.springbootredis.service.impl;

import com.springboot.springbootredis.model.User;
import com.springboot.springbootredis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Version: 1.0
 * @Desc:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final Map<Long, User> USER_MAP = new HashMap<>();

    static {
        USER_MAP.put(1L, new User(1L, "geekdigging.com", 18));
        USER_MAP.put(2L, new User(2L, "geekdigging.com", 19));
        USER_MAP.put(3L, new User(3L, "geekdigging.com", 20));
    }

    @CachePut(value = "user", key = "#user.id")
    @Override
    public User save(User user) {
        USER_MAP.put(user.getId(), user);
        log.info("进入 save 方法，当前存储对象：{}",  user);
        return user;
    }

    @Cacheable(value = "user", key = "#id")
    @Override
    public User get(Long id) {
        log.info("进入 get 方法，当前获取对象：{}",  USER_MAP.get(id));
        return USER_MAP.get(id);
    }

    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Long id) {
        USER_MAP.remove(id);
        log.info("进入 delete 方法，删除成功");
    }
}
