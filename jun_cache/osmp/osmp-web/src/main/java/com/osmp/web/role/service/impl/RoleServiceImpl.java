/*   
 * Project: OSMP
 * FileName: RoleServiceImpl.java
 * version: V1.0
 */
package com.osmp.web.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.role.dao.RoleMapper;
import com.osmp.web.role.dao.RolePrivilegeMapper;
import com.osmp.web.role.entity.Role;
import com.osmp.web.role.service.RoleService;
import com.osmp.web.user.dao.UserMapper;
import com.osmp.web.utils.Pagination;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月18日
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RolePrivilegeMapper rolePrivilegeMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<Role> selectAll(Pagination<Role> p, String where) {
		return roleMapper.selectByPage(p, Role.class, where);
	}

	@Override
	public Role getRoleById(String id) {
		return roleMapper.getById(id);
	}

	@Override
	public void deletRole(Role role) {
		userMapper.deletByRoleId(role.getId());
		rolePrivilegeMapper.deletByRoleId(role.getId());
		roleMapper.delete(role);
	}

}
