package com.baomidou.springwind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.Permission;
import com.baomidou.springwind.service.IPermissionService;
import com.baomidou.springwind.service.IRolePermissionService;

/**
 * <p>
 * 角色管理相关操作
 * </p>
 *
 *
 * @Author hubin
 * @Date 2016-04-15
 */
@Controller
@RequestMapping("/perm/permission")
public class PermissionController extends BaseController {

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IRolePermissionService rolePermissionService;

	@com.baomidou.kisso.annotation.Permission("2003")
	@RequestMapping("/list")
	public String list(Model model) {
		return "/permission/list";
	}

	@ResponseBody
	@com.baomidou.kisso.annotation.Permission("2003")
	@RequestMapping("/getPermissionList")
	public String getPermissionList() {
		Page<Permission> page = getPage();
		return jsonPage(permissionService.selectPage(page, null));
	}

	@ResponseBody
	@com.baomidou.kisso.annotation.Permission("2003")
	@RequestMapping("/delete/{permId}")
	public String delete(@PathVariable Long permId) {
		boolean exist = rolePermissionService.existRolePermission(permId);
		if (exist) {
			return "false";
		}
		return booleanToString(permissionService.deleteById(permId));
	}

}
