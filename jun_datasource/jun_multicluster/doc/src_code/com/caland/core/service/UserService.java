package com.caland.core.service;

import java.util.List;

import com.caland.common.page.Pagination;
import com.caland.core.bean.User;
import com.caland.core.query.UserQuery;

/**
 * 
 * @author lixu
 * @Date [2014-3-28 下午01:50:28]
 */
public interface UserService {
	/**
	 * 基本插入
	 * 
	 * @return
	 */
	public Integer addUser(User user);

	/**
	 * 根据主键查询
	 */
	public User getUserByKey(Integer id);

	/**
	 * 根据主键批量查询
	 */
	public List<User> getUserByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * 
	 * @return
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateUserByKey(User user);

	/**
	 * 根据条件查询分页查询
	 * 
	 * @param userQuery
	 *            查询条件
	 * @return
	 */
	public Pagination getUserListWithPage(UserQuery userQuery);

	/**
	 * 根据条件查询
	 * 
	 * @param userQuery
	 *            查询条件
	 * @return
	 */
	public List<User> getUserList(UserQuery userQuery);
}
