package com.jun.plugin.redis.demo.service.impl;

import org.springframework.stereotype.Service;

import com.jun.plugin.redis.demo.dao.SysUserDao;
import com.jun.plugin.redis.demo.pojo.entity.SysUser;
import com.jun.plugin.redis.demo.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zch
 */
@Service
public class SysUserServiceImpl implements SysUserService {


	@Resource
	private SysUserDao sysUserDao;

	//=====================================业务处理 start=====================================

	@Override
	public List<SysUser> findAll() {
		return sysUserDao.findAll();
	}
	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}
