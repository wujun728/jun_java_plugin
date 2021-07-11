package com.example.demo.service;


import com.example.demo.pojo.entity.SysUser;

import java.util.List;

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
