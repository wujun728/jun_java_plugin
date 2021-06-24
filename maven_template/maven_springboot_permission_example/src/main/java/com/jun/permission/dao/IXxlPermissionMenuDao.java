package com.jun.permission.dao;

import com.jun.permission.core.model.XxlPermissionMenu;

import java.util.List;

/**
 * @author wuXxl
 */
public interface IXxlPermissionMenuDao {

	public int add(XxlPermissionMenu XxlPermissionMenu);
	public int delete(int id);
	public int update(XxlPermissionMenu XxlPermissionMenu);

	public XxlPermissionMenu load(int id);
	public List<XxlPermissionMenu> getAllMenus();
	public List<XxlPermissionMenu> getMenusByRoleId(int roleId);
	public List<XxlPermissionMenu> getMenusByParentId(int parentId);

	public int findBindRoleCount(int menuId);
}
