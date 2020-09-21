package com.osmp.web.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.user.entity.SysPrivilege;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 上午11:21:09
 */

public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {
	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	public List<SysPrivilege> getAllSysPrivilege();

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public SysPrivilege getById(@Param("id") int id);

	/**
	 * 根据父ID查询
	 * 
	 * @param pid
	 * @return
	 */
	public List<SysPrivilege> getListByPid(@Param("pid") int pid);
}
