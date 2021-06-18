package com.jun.permission.dao;

import com.jun.permission.core.model.junPermissionRole;

import java.util.List;
import java.util.Set;


/**
 * @author wujun
 */
public interface IjunPermissionRoleDao {

	public int add(junPermissionRole junPermissionRole);
	public int delete(List<Integer> roleIds);
	public int update(junPermissionRole junPermissionRole);

	public junPermissionRole loadRole(int id);
	public List<junPermissionRole> getAllRoles();
	public int findUserRoleCount(int userId, int roleId);

	public int findUserCountByRole(int roleId);
	public List<junPermissionRole> findRoleByUserId(int userId);

	public int bindRoleMenu(int roleId, Set<Integer> menudIds);
	public int unBindRoleMenu(int[] roleIds);
	public int unBindRoleMenus(int roleId, Set<Integer> delMenudIds);

}
