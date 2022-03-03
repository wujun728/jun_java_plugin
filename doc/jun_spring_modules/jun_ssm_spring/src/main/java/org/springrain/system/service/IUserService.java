package org.springrain.system.service;

import org.springrain.system.entity.User;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see org.springrain.springrain.service.User
 */
public interface IUserService extends IBaseSpringrainService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveUser(User entity) throws Exception;

	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateUser(User entity) throws Exception;
	
	/**
	 * 根据用户Id 删除用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	String deleteUserById(String userId) throws Exception;

	
	
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	User findUserById(Object id) throws Exception;
    

	
	void updateRoleUser(String userId,String roleIds)throws Exception;

	
}
