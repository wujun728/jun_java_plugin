package com.osmp.web.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.user.dao.SysPrivilegeMapper;
import com.osmp.web.user.entity.SysPrivilege;
import com.osmp.web.user.service.SysPrivilegeService;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 上午11:29:07
 */
@Service
public class SysPrivilegeServiceImpl implements SysPrivilegeService {

	@Autowired
	private SysPrivilegeMapper mapper;

	@Override
	public void delete(SysPrivilege p) {
		mapper.delete(p);
	}

	@Override
	public List<SysPrivilege> getList() {
		return mapper.getAllSysPrivilege();
	}

	@Override
	public void insert(SysPrivilege p) {
		mapper.insert(p);
	}

	@Override
	public void update(SysPrivilege p) {
		mapper.update(p);
	}

	@Override
	public SysPrivilege get(SysPrivilege p) {
		return mapper.getById(p.getId());
	}

	@Override
	public List<SysPrivilege> getListByPid(int pid) {
		return mapper.getListByPid(pid);
	}

}
