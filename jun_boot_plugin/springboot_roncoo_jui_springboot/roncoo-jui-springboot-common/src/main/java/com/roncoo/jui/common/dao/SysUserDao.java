package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.SysUser;
import com.roncoo.jui.common.entity.SysUserExample;
import com.roncoo.jui.common.util.jui.Page;

public interface SysUserDao {
    int save(SysUser record);

    int deleteById(Long id);

    int updateById(SysUser record);

    SysUser getById(Long id);

    Page<SysUser> listForPage(int pageCurrent, int pageSize, SysUserExample example);

	SysUser getByUserPhone(String userPhone);
	
	SysUser getByUserEmail(String userEmail);
}