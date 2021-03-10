package cn.springmvc.mybatis.service.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.mybatis.entity.auth.Role;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.mapper.auth.RoleMapper;
import cn.springmvc.mybatis.mapper.auth.UserMapper;
import cn.springmvc.mybatis.service.auth.AuthService;

@Service
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
