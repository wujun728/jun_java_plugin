package com.jun.permission.dao;

import com.jun.permission.core.model.junPermissionUser;

import java.util.List;
import java.util.Set;

public interface IjunPermissionUserDao {

	public int add(junPermissionUser junPermissionUser);
	public int delete(List<Integer> userIds);
	public int update(junPermissionUser junPermissionUser);

	public junPermissionUser loadUser(int userId);
	public junPermissionUser findUserByUserName (String username);

	public List<junPermissionUser> queryUser(int offset, int pagesize, String userName);
	public int queryUserCount(int offset, int pagesize, String userName);

	public int bindUserRoles(int userId, Set<Integer> addRoldIds);
	public int unBindUserRoles(int userId, Set<Integer> delRoldIds);
	public int unBindUserRoleAll(int[] userIds);

}
