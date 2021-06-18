package cn.itcast.mis.req.bo.impl;

import java.util.List;

import cn.itcast.mis.req.bo.UserBO;
import cn.itcast.mis.req.dao.UserDAO;
import cn.itcast.mis.req.hibernate.UserT;

public class UserBOImpl implements UserBO {

	private UserDAO userDAO;
	
	public void addUser(UserT user) {
		// TODO Auto-generated method stub
		userDAO.saveUser(user);
	}

	public List<UserT> queryUsersList() {
		// TODO Auto-generated method stub
		return userDAO.listUsers();
	}

	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		userDAO.deleteUser(id);
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public UserT findUser(Integer id) {
		
		return userDAO.loadUser(id);
	}

	public List<UserT> findUserByName(String name) {
		// TODO Auto-generated method stub
		
		return userDAO.queryUserByName(name);
	}

}
