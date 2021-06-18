package com.roncoo.jui.common.dao;

import java.util.List;

import com.roncoo.jui.common.entity.SysRoleUser;
import com.roncoo.jui.common.entity.SysRoleUserExample;
import com.roncoo.jui.common.util.jui.Page;


public interface SysRoleUserDao {
	int save(SysRoleUser record);

	int deleteById(Long id);

	int updateById(SysRoleUser record);

	SysRoleUser getById(Long id);

	Page<SysRoleUser> listForPage(int pageCurrent, int pageSize, SysRoleUserExample example);

	List<SysRoleUser> listByUserId(Long userId);

	int deleteByUserId(Long userId);

}