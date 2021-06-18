package cn.yunhe.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

import cn.yunhe.model.User;

public interface IUserService {
	User selectByPrimaryKey(int userId);

	List<User> selectAll();

	int addUser(User user);

	int delUser(User user);
	
	Integer selectByNameAndPass(User user);
	
	Page queryUsersByPage(int currPage,int pageSize);
	
	User getUser(User user);
	
	void editUser(User user);
	
	Page queryLikeUsers(Map<String, Object> cond);
	
}	
