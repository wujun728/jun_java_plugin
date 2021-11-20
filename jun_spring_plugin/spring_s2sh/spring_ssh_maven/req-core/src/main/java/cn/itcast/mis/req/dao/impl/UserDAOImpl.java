package cn.itcast.mis.req.dao.impl;

import java.util.List;

import cn.itcast.mis.req.dao.UserDAO;
import cn.itcast.mis.req.hibernate.UserT;

public class UserDAOImpl extends BasicDAO implements UserDAO {

	public void saveUser(UserT u) {
		// TODO Auto-generated method stub
		this.saveObject(u);
	}

	public List<UserT> listUsers() {
		// TODO Auto-generated method stub
		return this.searchByHQL("from UserT");
	}

	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		this.deleteObj(UserT.class,id);
	}

	public UserT loadUser(Integer id) {
		// TODO Auto-generated method stub
		UserT user = (UserT)this.searchObjById(UserT.class, id);
		return user;
	}

	public List<UserT> queryUserByName(String name) {
		// TODO Auto-generated method stub
		return this.searchByHQL("from UserT where userName='"+name+"'");
	}

	public void deleteUserList(List<UserT> list) {
		// TODO Auto-generated method stub
		this.deleteUserList(list);
	}

}
