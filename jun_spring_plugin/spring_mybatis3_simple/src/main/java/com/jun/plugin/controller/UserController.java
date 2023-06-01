package com.jun.plugin.controller;

import java.util.List;

import com.jun.plugin.entity.UserEntity;
import com.jun.plugin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=userMapper.getAll();
		System.out.println(users.toString());
		return users;
	}
	
    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	System.out.println("id"+id);
    	UserEntity user=userMapper.getOne(id);
		System.out.println(user.toString());
        return user;
    }
    
    @RequestMapping("/add")
    public void save(UserEntity user) {
    	userMapper.insert(user);
		System.out.println(user.toString());
    }
    
    @RequestMapping(value="/update")
    public void update(UserEntity user) {
    	userMapper.update(user);
		System.out.println(user.toString());
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
		System.out.println(id);
    }
    
    
}