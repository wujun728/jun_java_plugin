package com.jun.permission.dao;

import com.jun.permission.core.model.junPermissionMenu;

import java.util.List;

/**
 * @author wujun
 */
public interface IjunPermissionMenuDao {

	public int add(junPermissionMenu junPermissionMenu);
	public int delete(int id);
	public int update(junPermissionMenu junPermissionMenu);

	public junPermissionMenu load(int id);
	public List<junPermissionMenu> getAllMenus();
	public List<junPermissionMenu> getMenusByRoleId(int roleId);
	public List<junPermissionMenu> getMenusByParentId(int parentId);

	public int findBindRoleCount(int menuId);
}
