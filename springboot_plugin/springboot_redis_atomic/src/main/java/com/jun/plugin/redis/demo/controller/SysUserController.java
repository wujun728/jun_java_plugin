package com.jun.plugin.redis.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.redis.demo.pojo.entity.SysUser;
import com.jun.plugin.redis.demo.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class SysUserController {

	@Resource
	private SysUserService sysUserService;

	//=====================================业务处理 start=====================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<SysUser> list() {
		List<SysUser> sysUserList = sysUserService.findAll();
		return sysUserList;
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}
