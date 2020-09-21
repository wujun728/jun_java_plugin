package com.osmp.web.user.service;

import java.util.List;

import com.osmp.web.user.entity.SysPrivilege;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 上午11:23:35
 */

public interface SysPrivilegeService {

	/**
	 * 根据ID查询
	 * 
	 * @param p
	 * @return
	 */
	public SysPrivilege get(SysPrivilege p);

	/**
	 * 查询所有菜单项
	 * 
	 * @return
	 */
	public List<SysPrivilege> getList();

	/**
	 * 查询所有菜单项
	 * 
	 * @return
	 */
	public List<SysPrivilege> getListByPid(int pid);

	/**
	 * 新增一个菜单项
	 * 
	 * @param p
	 */
	public void insert(SysPrivilege p);

	/**
	 * 删除一个菜单项
	 * 
	 * @param p
	 */
	public void delete(SysPrivilege p);

	/**
	 * 修改一个菜单项
	 * 
	 * @param p
	 */
	public void update(SysPrivilege p);

}
