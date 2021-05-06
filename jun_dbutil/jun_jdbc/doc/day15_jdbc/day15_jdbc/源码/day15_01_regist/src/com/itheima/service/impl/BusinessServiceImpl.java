package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.exception.UserHasExistException;
import com.itheima.service.BusinessService;
import com.itheima.util.DaoFactory;

public class BusinessServiceImpl implements BusinessService {
	private UserDao dao = DaoFactory.getUserDao();
	public void regist(User user) throws UserHasExistException {
		//根据用户名查询用户名是否存在
		User dbUser = dao.findByUsername(user.getUsername());
		if(dbUser!=null)
			throw new UserHasExistException("用户名："+user.getUsername()+"应经存在了");
		dao.save(user);
	}

	public User login(String username, String password) {
		return dao.findUser(username,password);
	}

}
