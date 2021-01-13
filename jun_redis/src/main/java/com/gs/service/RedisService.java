package com.gs.service;

import com.gs.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public String getById(int id) {
        // 查找用户
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(id + "");
        if (value != null) {
            System.out.println("从缓存中拿数据");
            return value;
        } else {
            // 去数据库里拿
            String a = "test";
            valueOperations.set(id + "", a);
            return a;
        }
    }

    public List<User> listAll() {
        ListOperations<String, User> listOperations = redisTemplate.opsForList();
        List<User> userList = listOperations.range("list_user", 0, 20);
        if (userList != null && userList.size() > 0) {
            System.out.println("从缓存中拿数据");
            return userList;
        } else {
            // 模拟从数据库拿数据
            List<User> userList1 = new ArrayList<User>();
            userList1.add(new User());
            listOperations.leftPushAll("list_user", userList1);
            return userList1;
        }
    }

}
