/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.mockito.UserManage.java
 * Class:			UserManage
 * Date:			2012-12-6
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.mockito;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-6 上午11:02:30 
 */

public class UserManage {
	private UserDao userDao;
	
	public User getUser(Integer id) {
		return userDao.get(id);
	}
	
	public void printUser(User user) {
		System.out.println(user);
	}
	
	/**  
	 * 返回 userDao 的值   
	 * @return userDao  
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**  
	 * 设置 userDao 的值  
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
}
