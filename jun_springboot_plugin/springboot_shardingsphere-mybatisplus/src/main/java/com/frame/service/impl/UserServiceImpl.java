package com.frame.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.entity.User;
import com.frame.mapper.UserMapper;
import com.frame.service.UserService;
import org.springframework.stereotype.Service;


/**
 * @author wenbin
 * @Description: 用户实现类
 * @date 2019/8/8 上午9:13
 */
@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {
}
