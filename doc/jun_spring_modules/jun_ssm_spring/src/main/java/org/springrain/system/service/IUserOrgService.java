package org.springrain.system.service;

import java.util.List;

import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.Org;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserOrg;


/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 15:28:18
 * @see org.springrain.springrain.service.TuserOrg
 */
public interface IUserOrgService extends IBaseSpringrainService {

	
	/**
	 * 根据部门Id 查找部门下的所有User对象
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<User> findUserByOrgId(String orgId) throws Exception;
	
	
	
	/**
	 * 根据部门Id 查找部门下的所有人员的userId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<String> findUserIdsByOrgId(String orgId) throws Exception;
	
	

	
	/**
	 * 根据部门ID,查找部门下(包括所有子部门)的User对象
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<User> findAllUserByOrgId(String orgId) throws Exception;
	
	
	

	/**
	 * 根据部门ID,查找部门下(包括所有子部门)的人员的userId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<String> findAllUserIdsByOrgId(String orgId) throws Exception;
	
	/**
	 * 根据用户ID 查找所在的部门
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<Org> findOrgByUserId(String userId) throws Exception;
	/**
	 * 根据用户ID 查找管理的部门
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<UserOrg> findManagerOrgByUserId(String userId) throws Exception;
	
	/**
	 * 根据用户ID 查找所在的部门的Id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<String> findOrgIdsByUserId(String userId) throws Exception;
	/**
	 * 根据部门ID 查找主管
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<User> findManagerUserByOrgId(String orgId) throws Exception;
	/**
	 * 根据部门ID 查找主管Id
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<String> findManagerUserIdsByOrgId(String orgId) throws Exception;
	/**
	 *  根据主管ID 查找主管能够管理的部门Ids
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<String> findOrgIdsByManagerUserId(String managerUserId,Page page) throws Exception;
	
	/**
	 * 根据主管ID 查找主管能够管理的部门
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<Org> findOrgByManagerUserId(String managerUserId,Page page) throws Exception;
	
	
	
	
	/**
	 * 根据主管ID 查找主管能够管理的用户Id
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<String> findUserIdsByManagerUserId(String managerUserId,Page page) throws Exception;
	
	/**
	 * 根据主管ID 查找主管能够管理的用户
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<User> findUserByManagerUserId(String managerUserId,Page page) throws Exception;
	
	
	/**
	 *  根据主管ID 查找主管能够管理的部门Ids
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<String> findOrgIdsByManagerUserId(String managerUserId) throws Exception;
	
	/**
	 * 根据主管ID 查找主管能够管理的部门
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<Org> findOrgByManagerUserId(String managerUserId) throws Exception;
	
	
	
	
	/**
	 * 根据主管ID 查找主管能够管理的用户Id
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<String> findUserIdsByManagerUserId(String managerUserId) throws Exception;
	
	/**
	 * 根据主管ID 查找主管能够管理的用户
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<User> findUserByManagerUserId(String managerUserId) throws Exception;
	
	
	
	/**
	 * 返回根据主管子查询的orgId的sql语句
	 * @param managerUserId
	 * @return
	 * @throws Exception
	 */
	Finder findOrgIdsSQLByManagerUserId(String managerUserId) throws Exception;
	
	
	/**
	 * 返回根据主管子查询的userId的sql语句
	 * @param managerUserId
	 * @return
	 * @throws Exception
	 */
	Finder findUserIdsSQLByManagerUserId(String managerUserId) throws Exception;
	
	
	
	/**
	 * 根据部门ID,查找部门下(包括所有子部门)的人员数量
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	Integer findAllUserCountByOrgId(String orgId) throws Exception;
	
	
	
}
