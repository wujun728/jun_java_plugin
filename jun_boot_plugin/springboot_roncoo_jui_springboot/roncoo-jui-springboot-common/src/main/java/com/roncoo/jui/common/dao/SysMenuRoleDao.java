package com.roncoo.jui.common.dao;

import java.util.List;

import com.roncoo.jui.common.entity.SysMenuRole;
import com.roncoo.jui.common.entity.SysMenuRoleExample;
import com.roncoo.jui.common.util.jui.Page;

public interface SysMenuRoleDao {
	int save(SysMenuRole record);

	int deleteById(Long id);

	int updateById(SysMenuRole record);

	SysMenuRole getById(Long id);

	Page<SysMenuRole> listForPage(int pageCurrent, int pageSize, SysMenuRoleExample example);

	List<SysMenuRole> listByRoleId(Long roleId);

	int deleteByRoleId(Long roleId);
}