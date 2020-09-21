/*   
 * Project: OSMP
 * FileName: RoleService.java
 * version: V1.0
 */
package com.osmp.web.role.service;

import java.util.List;

import com.osmp.web.role.entity.Role;
import com.osmp.web.utils.Pagination;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月18日
 */

public interface RoleService {

	/**
	 * 按条件查询用户
	 * @param where
	 * @return
	 */
	public List<Role> selectAll(Pagination<Role> p, String where);
	
	public Role getRoleById(String id);
	
	/**
	 * 删除角色，关联删除中间表
	 * @param role
	 */
	public void deletRole(Role role);

}
