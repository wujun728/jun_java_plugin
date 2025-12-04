package com.flying.cattle.wf.service.impl;

import org.springframework.stereotype.Service;

import com.flying.cattle.wf.aid.ServiceImpl;
import com.flying.cattle.wf.dao.UserRepository;
import com.flying.cattle.wf.entity.User;
import com.flying.cattle.wf.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserRepository, User> implements UserService {

}
