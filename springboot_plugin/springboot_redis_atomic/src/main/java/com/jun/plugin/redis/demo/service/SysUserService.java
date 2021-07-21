package com.jun.plugin.redis.demo.service;


import java.util.List;

import com.jun.plugin.redis.demo.pojo.entity.SysUser;

/**
 * @author zch
 */
public interface SysUserService {

	/**
	 * 查询所有数据
	 *
	 * @return
	 */
	List<SysUser> findAll();

}
