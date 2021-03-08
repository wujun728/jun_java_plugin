package com.sam.demo.spring.boot.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sam.demo.spring.boot.mybatis.entity.User;
import com.sam.demo.spring.boot.mybatis.entity.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public User findOne(Long id){
		return userMapper.selectByPrimaryKey(id);
	}
	
	@Transactional
	public User updateUser(User user){
		userMapper.updateByPrimaryKey(user);
		user.setMobilePhoneNumber("13900000008");
		userMapper.updateByPrimaryKey(user);
		return user;
	}
	
	@Transactional
	public User deleteUser(Long id){
		User user = userMapper.selectByPrimaryKey(id);
		userMapper.deleteByPrimaryKey(id);
		return user;
	}
}
