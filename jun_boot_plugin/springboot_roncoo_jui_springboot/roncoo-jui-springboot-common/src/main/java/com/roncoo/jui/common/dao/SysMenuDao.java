package com.roncoo.jui.common.dao;

import java.util.List;

import com.roncoo.jui.common.entity.SysMenu;
import com.roncoo.jui.common.entity.SysMenuExample;
import com.roncoo.jui.common.util.jui.Page;

public interface SysMenuDao {
	int save(SysMenu record);

	int deleteById(Long id);

	int updateById(SysMenu record);

	SysMenu getById(Long id);

	Page<SysMenu> listForPage(int pageCurrent, int pageSize, SysMenuExample example);

	List<SysMenu> listByParentId(Long parentId);
}