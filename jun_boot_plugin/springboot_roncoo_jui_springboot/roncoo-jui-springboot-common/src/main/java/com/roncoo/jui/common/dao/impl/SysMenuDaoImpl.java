package com.roncoo.jui.common.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roncoo.jui.common.dao.SysMenuDao;
import com.roncoo.jui.common.entity.SysMenu;
import com.roncoo.jui.common.entity.SysMenuExample;
import com.roncoo.jui.common.mapper.SysMenuMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;

@Repository
public class SysMenuDaoImpl implements SysMenuDao {
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public int save(SysMenu record) {
		return this.sysMenuMapper.insertSelective(record);
	}

	@Override
	public int deleteById(Long id) {
		return this.sysMenuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateById(SysMenu record) {
		return this.sysMenuMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysMenu getById(Long id) {
		return this.sysMenuMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<SysMenu> listForPage(int pageCurrent, int pageSize, SysMenuExample example) {
		int count = this.sysMenuMapper.countByExample(example);
		pageSize = PageUtil.checkPageSize(pageSize);
		pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
		int totalPage = PageUtil.countTotalPage(count, pageSize);
		example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
		example.setPageSize(pageSize);
		return new Page<SysMenu>(count, totalPage, pageCurrent, pageSize, this.sysMenuMapper.selectByExample(example));
	}

	@Override
	public List<SysMenu> listByParentId(Long parentId) {
		SysMenuExample example = new SysMenuExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		example.setOrderByClause(" sort desc, id desc ");
		return this.sysMenuMapper.selectByExample(example);
	}
}