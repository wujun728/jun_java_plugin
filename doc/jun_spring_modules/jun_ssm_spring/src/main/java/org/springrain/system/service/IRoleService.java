package org.springrain.system.service;

import org.springrain.system.entity.Role;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:02:59
 * @see org.springrain.springrain.service.Role
 */
public interface IRoleService extends IBaseSpringrainService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveRole(Role entity) throws Exception;
	
	
	/**
	 * 删除角色,并级联删除角色人员关系和角色菜单关系
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	String deleteRoleById(String roleId) throws Exception;
	
	
	/**
	 * 修改或者保存,根据id是否为空判断
	 * @param entity
	 * @return
	 * @throws Exception
	 */
    String saveorupdateRole(Role entity) throws Exception;
	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateRole(Role entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Role findRoleById(Object id) throws Exception;
	/**
	 * 根据id查询name
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	String findNameById(String roleId)throws Exception;
	
}
