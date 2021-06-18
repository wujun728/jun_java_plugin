package cn.itcast.mis.req.bo;

import java.util.List;

import cn.itcast.mis.req.hibernate.UserT;

public interface UserBO {

	void addUser(UserT user);
	
	List<UserT> queryUsersList();
	
	void deleteUser(Integer id);
	
	UserT findUser(Integer id);
	
	List<UserT> findUserByName(String name);
}
