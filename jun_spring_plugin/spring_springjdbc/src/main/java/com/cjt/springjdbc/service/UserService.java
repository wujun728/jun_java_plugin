package com.cjt.springjdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjt.springjdbc.dao.IUserDao;
import com.cjt.springjdbc.model.User;

@Service("userService")
public class UserService implements IUserService{
	
	@Autowired
	private IUserDao userDao;

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub	
		return userDao.findById(id);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	@Override
	public void saveOrUpdate(User user) {
		// TODO Auto-generated method stub
		if (user.getId() == null || findById(user.getId()) == null ) {
			userDao.save(user);
		} else {
			userDao.update(user);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		userDao.delete(id);
	}

}
