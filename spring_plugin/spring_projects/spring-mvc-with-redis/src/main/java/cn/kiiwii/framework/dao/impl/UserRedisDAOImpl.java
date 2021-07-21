package cn.kiiwii.framework.dao.impl;

import cn.kiiwii.framework.controller.User;
import cn.kiiwii.framework.dao.IUserRedisDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2016/9/19.
 */
@Repository("userRedisDAO")
public class UserRedisDAOImpl implements IUserRedisDAO{

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Object get(final String key) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(key);
        return o;
    }

    @Override
    public void set(String name, String s) {

    }

    @Override
    public void set(String name, User user) {

    }

    @Override
    public void set(String name, List<User> users) {

    }
}
