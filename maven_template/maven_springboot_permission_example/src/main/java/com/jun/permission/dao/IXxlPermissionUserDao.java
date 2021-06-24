package com.jun.permission.dao;

import java.util.List;
import java.util.Set;

import com.jun.permission.core.model.XxlPermissionUser;

public interface IXxlPermissionUserDao {

	public int add(XxlPermissionUser XxlPermissionUser);
	public int delete(List<Integer> userIds);
	public int update(XxlPermissionUser XxlPermissionUser);

	public XxlPermissionUser loadUser(int userId);
	public XxlPermissionUser findUserByUserName (String username);

	public List<XxlPermissionUser> queryUser(int offset, int pagesize, String userName);
	public int queryUserCount(int offset, int pagesize, String userName);

	public int bindUserRoles(int userId, Set<Integer> addRoldIds);
	public int unBindUserRoles(int userId, Set<Integer> delRoldIds);
	public int unBindUserRoleAll(int[] userIds);

}
