package com.jun.plugin.mybatis.service.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.mybatis.mapper.auth.RoleMapper;
import com.jun.plugin.mybatis.mapper.auth.UserMapper;
import com.jun.plugin.mybatis.model.auth.Role;
import com.jun.plugin.mybatis.model.auth.User;
import com.jun.plugin.mybatis.service.auth.AuthService;

import java.util.List;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public Role findRoleByRoleCode(String roleCode) {
        return roleMapper.findRoleByCode(roleCode);
    }

    @Override
    public List<User> findUserByRoleCode(String roleCode) {
        return userMapper.findUserByRoleCode(roleCode);
    }

}
