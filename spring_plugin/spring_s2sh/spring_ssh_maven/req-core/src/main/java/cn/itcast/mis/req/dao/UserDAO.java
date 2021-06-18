package cn.itcast.mis.req.dao;

import java.util.List;

import cn.itcast.mis.req.hibernate.UserT;

public interface UserDAO {

	void saveUser(UserT u);
	
	List<UserT> listUsers();
	
	void deleteUser(Integer id);
	
	UserT loadUser(Integer id);
	
	List<UserT> queryUserByName(String name);
	
	void deleteUserList(List<UserT> list);
}
