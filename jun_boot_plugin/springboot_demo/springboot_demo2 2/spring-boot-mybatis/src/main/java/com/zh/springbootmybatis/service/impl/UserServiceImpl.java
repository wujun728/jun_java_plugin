package com.zh.springbootmybatis.service.impl;

import com.zh.springbootmybatis.dao.UserMapper;
import com.zh.springbootmybatis.model.User;
import com.zh.springbootmybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Wujun
 * @date 2019/5/31
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new RuntimeException("user未查得"));
    }

    @Override
    public List<User> listByAge(Integer age) {
        return this.userMapper.listByAge(age);
    }

    @Override
    public void save(User user) {
        Optional.ofNullable(user.getId())
                .map(e -> this.userMapper.updateByPrimaryKeySelective(user))
                .orElseGet(() -> this.userMapper.insertSelective(user));
        log.info("=======================编号为:{}的用户被保存啦====================",user.getId());
    }

    @Override
    public void save(List<User> users) {
        this.userMapper.saveBatch(users);
    }

    @Override
    public void deleteById(Integer id) {
        this.userMapper.deleteByPrimaryKey(id);
    }
}
