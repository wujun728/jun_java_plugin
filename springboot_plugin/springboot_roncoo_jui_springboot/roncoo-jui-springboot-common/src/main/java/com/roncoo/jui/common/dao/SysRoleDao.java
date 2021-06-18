package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.SysRole;
import com.roncoo.jui.common.entity.SysRoleExample;
import com.roncoo.jui.common.util.jui.Page;

public interface SysRoleDao {
	int save(SysRole record);

	int deleteById(Long id);

	int updateById(SysRole record);

	SysRole getById(Long id);

	Page<SysRole> listForPage(int pageCurrent, int pageSize, SysRoleExample example);
}