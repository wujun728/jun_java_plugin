package com.example.demo.service.impl;

import com.example.demo.dao.SysUserDao;
import com.example.demo.pojo.entity.SysUser;
import com.example.demo.service.SysUserService;
import org.springframework.stereotype.Service;

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
