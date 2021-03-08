package com.springboot.springbootmybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootmybatisplus.mapper.UserMapper;
import com.springboot.springbootmybatisplus.model.User;
import com.springboot.springbootmybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/30
 * @Time: 0:02
 * @email: inwsy@hotmail.com
 * Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
