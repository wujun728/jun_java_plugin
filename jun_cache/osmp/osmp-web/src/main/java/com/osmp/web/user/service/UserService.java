package com.osmp.web.user.service;

import java.util.List;

import com.osmp.web.user.entity.User;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年8月22日 上午10:51:50
 */

public interface UserService {

	public User getUserById(String id);

	/**
	 * 按条件查询用户
	 * @param where
	 * @return
	 */
	public List<User> selectAll(Pagination<User> p, String where);
	
	/**
	 * 根据用户Id查询用户权限
	 * @param userId
	 * @return
	 */
	public List<Integer> selectUserPrivilege(String id);
	
	/**
	 * 判断用户是超级管理员
	 * @param id
	 * @return
	 */
	public boolean isAdminRole(String userId);
	
	/**
	 * 删除用户角色
	 * @param userId
	 */
	public void deletByUserId(String userId);
	
	public void deletUser(User user);

}
