package cn.yunhe.dao;

import java.util.List;
import java.util.Map;

import cn.yunhe.model.User;

public interface IUserDao {
	User selectByPrimaryKey(int userId);

	List<User> selectAll();

	int addUser(User user);

	int delUser(User user);
	
	Integer selectByNameAndPass(User user);	
	
	User getUser(User user);
		
	void editUser(User user);
		
	/**
	 * 模糊查询匹配的用户列表
	 */
	public List<Object> getLikeUsers(Map<String,Object> user);
	/**
	 * 模糊查询匹配的用户的数量	
	 */
	public Integer getLikeUsersCount(Map<String,Object> user);
	
	
}
