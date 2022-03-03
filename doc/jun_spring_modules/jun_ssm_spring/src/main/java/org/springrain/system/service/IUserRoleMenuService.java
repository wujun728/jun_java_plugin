package org.springrain.system.service;

import java.util.List;
import java.util.Set;

import org.springrain.system.entity.Menu;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;


/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see org.springrain.springrain.service.UserRole
 */
public interface IUserRoleMenuService extends IBaseSpringrainService {
/**
 * 根据用户ID 获取角色
 * @param UserId
 * @return
 * @throws Exception
 */
	List<Role> findRoleByUserId(String userId) throws Exception;
	/**
	 * 根据角色 获取能够访问的菜单
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	List<Menu> findMenuByRoleId(String roleId) throws Exception;
	/**
	 * 获取角色下的所有人员
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	List<User> findUserByRoleId(String roleId) throws Exception;
	/**
	 * 根据userId 查询能够看到的菜单  只查导航菜单
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<Menu> findMenuByUserId(String userId) throws Exception;
	
	/**
	 * 根据userId 查询能够看到的菜单  查所有菜单
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<Menu> findMenuByUserIdAll(String userId) throws Exception;
	
	
	/**
	 * 根据userId 查询能够看到的菜单,递归拼接层级结构
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<Menu> findMenuAndLeafByUserId(String userId) throws Exception;
	
	
	/**
	 * 根据roleId,查询角色的信息,并包括权限
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	Role findRoleAndMenu(String roleId)throws Exception;
	

	/**
	 * 根据账号密码 验证是否能够登陆,userType用于区分用户类型
	 * @param account
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	User findLoginUser(String account,String password,Integer userType) throws Exception;
	
/**
 * 查询数据库所有的角色和对应的菜单
 * @return
 * @throws Exception
 */
	List<Role> findAllRoleAndMenu()throws Exception;
	
	/**
	 * 递归获取 menuID 下的所有菜单
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	Menu findMenuAndLeaf(String menuId)throws Exception;
	/**
	 * 更新角色和menu的对应关系
	 * @param roleId
	 * @param menus
	 * @throws Exception
	 */
 // void updateRoleMenu(String roleId,String[] menus)throws Exception;
  /**
   * 根据UserId 获取用户的角色
   * @param userId
   * @return
   * @throws Exception
   */
  
	Set<String> getRolesAsString(String userId) throws Exception;
	/**
	 * 根据用户Id 获取所有的权限
	 * @param userId
	 * @return
	 * @throws Exception
	 */
    Set<String> getPermissionsAsString(String userId) throws Exception;	
	
	
}
