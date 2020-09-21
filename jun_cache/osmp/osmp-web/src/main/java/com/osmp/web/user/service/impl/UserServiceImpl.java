package com.osmp.web.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.user.dao.UserMapper;
import com.osmp.web.user.entity.User;
import com.osmp.web.user.service.UserService;
import com.osmp.web.utils.Pagination;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月20日
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User getUserById(String id) {
		return userMapper.getById(id);
	}

	@Override
	public List<User> selectAll(Pagination<User> p, String where) {
		return userMapper.selectByPage(p, User.class, where);
	}

	@Override
	public List<Integer> selectUserPrivilege(String id) {
		return userMapper.selectUserPrivilege(id);
	}

	@Override
	public boolean isAdminRole(String userId) {
		User u = userMapper.getUser(userId, SystemConstant.ADMIN_ROLE_ID);
		if(u == null)
			return false;
		return true;
	}

	@Override
	public void deletByUserId(String userId) {
		userMapper.deletByUserId(userId);
	}

	@Override
	public void deletUser(User user) {
		userMapper.delete(user);
		userMapper.deletByUserId(user.getId());
	}

}
