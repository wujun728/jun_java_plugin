package com.jun.plugin.mq.activemq.service.impl;

import com.baomidou.framework.service.impl.CommonServiceImpl;
import com.jun.plugin.mq.activemq.entity.User;
import com.jun.plugin.mq.activemq.mapper.UserMapper;
import com.jun.plugin.mq.activemq.service.UserService;

import org.springframework.stereotype.Service;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User> implements UserService {


}