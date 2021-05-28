package com.roncoo.jui.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roncoo.jui.common.dao.SysRoleDao;
import com.roncoo.jui.common.entity.SysRole;
import com.roncoo.jui.common.entity.SysRoleExample;
import com.roncoo.jui.common.mapper.SysRoleMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;

@Repository
public class SysRoleDaoImpl implements SysRoleDao {
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public int save(SysRole record) {
		return this.sysRoleMapper.insertSelective(record);
	}

	@Override
	public int deleteById(Long id) {
		return this.sysRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateById(SysRole record) {
		return this.sysRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysRole getById(Long id) {
		return this.sysRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<SysRole> listForPage(int pageCurrent, int pageSize, SysRoleExample example) {
		int count = this.sysRoleMapper.countByExample(example);
		pageSize = PageUtil.checkPageSize(pageSize);
		pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
		int totalPage = PageUtil.countTotalPage(count, pageSize);
		example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
		example.setPageSize(pageSize);
		return new Page<SysRole>(count, totalPage, pageCurrent, pageSize, this.sysRoleMapper.selectByExample(example));
	}
}