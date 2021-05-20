package com.roncoo.jui.common.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roncoo.jui.common.dao.SysUserDao;
import com.roncoo.jui.common.entity.SysUser;
import com.roncoo.jui.common.entity.SysUserExample;
import com.roncoo.jui.common.mapper.SysUserMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.xiaoleilu.hutool.util.CollectionUtil;

@Repository
public class SysUserDaoImpl implements SysUserDao {
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public int save(SysUser record) {
		return this.sysUserMapper.insertSelective(record);
	}

	@Override
	public int deleteById(Long id) {
		return this.sysUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateById(SysUser record) {
		return this.sysUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysUser getById(Long id) {
		return this.sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<SysUser> listForPage(int pageCurrent, int pageSize, SysUserExample example) {
		int count = this.sysUserMapper.countByExample(example);
		pageSize = PageUtil.checkPageSize(pageSize);
		pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
		int totalPage = PageUtil.countTotalPage(count, pageSize);
		example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
		example.setPageSize(pageSize);
		return new Page<SysUser>(count, totalPage, pageCurrent, pageSize, this.sysUserMapper.selectByExample(example));
	}

	@Override
	public SysUser getByUserPhone(String userPhone) {
		SysUserExample example = new SysUserExample();
		example.createCriteria().andUserPhoneEqualTo(userPhone);
		List<SysUser> list = this.sysUserMapper.selectByExample(example);
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public SysUser getByUserEmail(String userEmail) {
		SysUserExample example = new SysUserExample();
		example.createCriteria().andUserEmailEqualTo(userEmail);
		List<SysUser> list = this.sysUserMapper.selectByExample(example);
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}