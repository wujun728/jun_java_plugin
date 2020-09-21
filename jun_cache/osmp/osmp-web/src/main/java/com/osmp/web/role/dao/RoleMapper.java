/*   
 * Project: OSMP
 * FileName: RoleMapper.java
 * version: V1.0
 */
package com.osmp.web.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.role.entity.Role;

public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public Role getById(@Param("id") String id);
	
	/**
	 * 根据用户Id查询用户角色对象
	 * @param userId
	 * @return
	 */
	public List<Role> getUserRole(@Param("id")String id);
	
}
