package com.baomidou.springwind.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.UserRole;
import com.baomidou.springwind.mapper.UserRoleMapper;
import com.baomidou.springwind.service.IUserRoleService;
import com.baomidou.springwind.service.support.BaseServiceImpl;

/**
 *
 * UserRole 表数据服务层接口实现类
 *
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

	@Override
	public boolean existRoleUser( Long roleId ) {
		UserRole ur = new UserRole();
		ur.setRid(roleId);
		int rlt = baseMapper.selectCount(new EntityWrapper<UserRole>(ur));
		return rlt >= 1;
	}

}