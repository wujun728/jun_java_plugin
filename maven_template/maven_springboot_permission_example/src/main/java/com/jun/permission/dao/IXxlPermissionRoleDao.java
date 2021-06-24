package com.jun.permission.dao;

import java.util.List;
import java.util.Set;

import com.jun.permission.core.model.XxlPermissionRole;


/**
 * @author wujun
 */
public interface IXxlPermissionRoleDao {

	public int add(XxlPermissionRole XxlPermissionRole);
	public int delete(List<Integer> roleIds);
	public int update(XxlPermissionRole XxlPermissionRole);

	public XxlPermissionRole loadRole(int id);
	public List<XxlPermissionRole> getAllRoles();
	public int findUserRoleCount(int userId, int roleId);

	public int findUserCountByRole(int roleId);
	public List<XxlPermissionRole> findRoleByUserId(int userId);

	public int bindRoleMenu(int roleId, Set<Integer> menudIds);
	public int unBindRoleMenu(int[] roleIds);
	public int unBindRoleMenus(int roleId, Set<Integer> delMenudIds);

}
