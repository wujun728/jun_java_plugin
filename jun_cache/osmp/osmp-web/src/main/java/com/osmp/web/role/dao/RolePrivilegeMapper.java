/*   
 * Project: OSMP
 * FileName: RoleService.java
 * version: V1.0
 */
package com.osmp.web.role.dao;

import org.apache.ibatis.annotations.Param;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.role.entity.RolePrivilege;

public interface RolePrivilegeMapper extends BaseMapper<RolePrivilege> {

	public void deletByRoleId(@Param("roleId")String roleId);
}
