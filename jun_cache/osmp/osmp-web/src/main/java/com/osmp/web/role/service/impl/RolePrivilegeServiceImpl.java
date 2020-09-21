/*   
 * Project: OSMP
 * FileName: RolePrivilegeServiceImpl.java
 * version: V1.0
 */
package com.osmp.web.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.role.dao.RolePrivilegeMapper;
import com.osmp.web.role.entity.RolePrivilege;
import com.osmp.web.role.service.RolePrivilegeService;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月18日
 */
@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

	@Autowired
	private RolePrivilegeMapper rolePrivilegeMapper;
	

	@Override
	public List<RolePrivilege> selectAll(String where) {
		return rolePrivilegeMapper.selectAll(RolePrivilege.class, where);
	}

	@Override
	public void deletByRoleId(String roleId) {
		rolePrivilegeMapper.deletByRoleId(roleId);
	}

}
