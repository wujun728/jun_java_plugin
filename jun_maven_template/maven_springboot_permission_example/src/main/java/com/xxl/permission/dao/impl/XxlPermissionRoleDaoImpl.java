package com.jun.permission.dao.impl;

import com.jun.permission.core.model.junPermissionRole;
import com.jun.permission.dao.IjunPermissionRoleDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wujun
 */
@Repository
public class junPermissionRoleDaoImpl implements IjunPermissionRoleDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int add(junPermissionRole junPermissionRole) {
		return sqlSessionTemplate.insert("junPermissionRoleMapper.add", junPermissionRole);
	}

	@Override
	public int delete(List<Integer> roleIds) {
		return sqlSessionTemplate.delete("junPermissionRoleMapper.delete", roleIds);
	}

	@Override
	public int update(junPermissionRole junPermissionRole) {
		return sqlSessionTemplate.update("junPermissionRoleMapper.update", junPermissionRole);
	}

	@Override
	public junPermissionRole loadRole(int id) {
		return sqlSessionTemplate.selectOne("junPermissionRoleMapper.loadRole", id);
	}

	@Override
	public List<junPermissionRole> getAllRoles() {
		return sqlSessionTemplate.selectList("junPermissionRoleMapper.getAllRoles");
	}

	@Override
	public int findUserRoleCount(int userId, int roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("roleId", roleId);
		return sqlSessionTemplate.selectOne("junPermissionRoleMapper.findUserRoleCount", params);
	}

	@Override
	public int findUserCountByRole(int roleId) {
		return sqlSessionTemplate.selectOne("junPermissionRoleMapper.findUserCountByRole", roleId);
	}

	@Override
	public List<junPermissionRole> findRoleByUserId(int userId) {
		return sqlSessionTemplate.selectList("junPermissionRoleMapper.findRoleByUserId", userId);
	}

	@Override
	public int bindRoleMenu(int roleId, Set<Integer> menudIds) {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("menudIds", menudIds);

		return sqlSessionTemplate.insert("junPermissionRoleMapper.bindRoleMenu", params);
	}

	@Override
	public int unBindRoleMenu(int[] roleIds) {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("roleIds", roleIds);
		return sqlSessionTemplate.delete("junPermissionRoleMapper.unBindRoleMenu", params);
	}

	@Override
	public int unBindRoleMenus(int roleId, Set<Integer> delMenudIds) {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("delMenudIds", delMenudIds);
		
		return sqlSessionTemplate.insert("junPermissionRoleMapper.unBindRoleMenus", params);
	}

}
