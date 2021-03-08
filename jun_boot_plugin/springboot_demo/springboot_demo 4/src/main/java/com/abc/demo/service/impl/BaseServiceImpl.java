package com.abc.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.demo.domain.User;
import com.abc.demo.mapper.UserMapper;
import com.abc.demo.service.UserService;
@Service
public class BaseServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	@Override
	public User findById(Integer id) {
		
		return userMapper.findById(id);
	}

	@Override
	public List<User> findByAll() {
		// TODO Auto-generated method stub
		return userMapper.findByAll();
	}

	@Override
	public int insert(User user) {
		
		return userMapper.insert(user);
	}

	@Override
	public int delete(Integer id) {
		
		return userMapper.deleteById(id);
	}

	@Override
	public int update(User user) {
		
		return  userMapper.update(user);
	}

}
