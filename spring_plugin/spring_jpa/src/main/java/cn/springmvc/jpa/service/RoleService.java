package cn.springmvc.jpa.service;

import java.util.Collection;

import cn.springmvc.jpa.entity.Role;
import cn.springmvc.jpa.entity.User;

/**
 * @author Wujun
 *
 */
public interface RoleService {

	/**
	 * 添加一个角色 ，若已经存在同名角色，则不创建
	 *
	 * @param role
	 *            角色对象
	 */
	public void addRole(Role role);

	/**
	 * 根据编码查询角色
	 *
	 * @param code
	 *            角色编码
	 * @return
	 */
	public Role findRoleByCode(String code);

	/**
	 * 根据用户查询对应所有角色
	 *
	 * @param user
	 *            用户
	 * @return roles 所有角色
	 */
	public Collection<Role> findRoles(User user);

	/**
	 * 给角色授权
	 *
	 * @param roleCode
	 *            角色编码
	 * @param permissionKey
	 *            授权对应的KEY
	 */
	public void addRolePermission(String roleCode, String permissionKey);

}
