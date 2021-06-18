package com.thinkingcao.silence.ssm.service.impl;

import com.thinkingcao.silence.ssm.entity.User;
import com.thinkingcao.silence.ssm.mapper.UserMapper;
import com.thinkingcao.silence.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

   //  注释掉set方法注入,换成注解版本
   /* public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }*/

    //声明式事物
    @Transactional(readOnly = true)
    @Override
    public void save(User u) {
        userMapper.insert(u);
        //int a = 1 / 0 ;//故意出错，演示是否会回滚
    }

    @Override
    public void update(User u) {
        userMapper.updateById(u);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User get(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> listAll() {
        return userMapper.selectAll();
    }
}