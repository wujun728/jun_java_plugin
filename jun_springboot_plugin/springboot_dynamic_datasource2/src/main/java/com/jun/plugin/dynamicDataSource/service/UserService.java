package com.jun.plugin.dynamicDataSource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jun.plugin.dynamicDataSource.entity.User;
import com.jun.plugin.dynamicDataSource.repository.UserMapper;

/**
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void testTransactional() {
        User user = new User();
        user.setUsername("Transactional");
        user.setPassword("Transactional");
        user.setSex(1);
        user.setAge(18);
        userMapper.save(user);
        User user1 = new User();
        user1.setId(2L);
        user1.setPassword("Transactional");
        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
        System.out.println(userMapper.update(user1));
        throw new RuntimeException();
    }


}
