/*   
 * Project: OSMP
 * FileName: RolePrivilegeService.java
 * version: V1.0
 */
package com.osmp.web.role.service;

import java.util.List;

import com.osmp.web.role.entity.RolePrivilege;

public interface RolePrivilegeService {
	
	/**
	 * 查询所有满足条件的角色权限
	 * @param where
	 * @return
	 */
	public List<RolePrivilege> selectAll(String where);
	
	/**
	 * 删除角色权限
	 * @param where
	 * @return
	 */
	public void deletByRoleId(String roleId);
}
