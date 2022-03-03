package com.erp.jee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.pageModel.Json;
import com.erp.jee.pageModel.Role;
import com.erp.jee.service.RoleServiceI;
import com.jun.plugin.utils.ExceptionUtil;

/**
 * 角色ACTION
 * 
 * @author Wujun
 * 
 */
//@Action(value = "roleAction", results = { @Result(name = "role", location = "/com/jeecg/role.jsp") })
@Controller
@RequestMapping("/roleAction.do")
public class RoleAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(RoleAction.class);

	private Role role = new Role();
	private RoleServiceI roleService;

	public RoleServiceI getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(RoleServiceI roleService) {
		this.roleService = roleService;
	}

	public Role getModel() {
		return role;
	}

	/**
	 * 跳转到角色管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=goRole")
	public String goRole(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "role";
	}

	/**
	 * 获得角色datagrid
	 */
	@RequestMapping(params = "method=datagrid")
	public void datagrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, roleService.datagrid(role));
	}

	/**
	 * 添加一个角色
	 */
	@RequestMapping(params = "method=add")
	public void add(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			roleService.add(role);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(response, j);
	}

	/**
	 * 编辑一个角色
	 */
	@RequestMapping(params = "method=edit")
	public void edit(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			roleService.update(role);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(response, j);
	}

	/**
	 * 删除一个或多个角色
	 */
	@RequestMapping(params = "method=delete")
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		roleService.delete(role.getIds());
		j.setSuccess(true);
		writeJson(response, j);
	}

	/**
	 * 获得角色下拉列表
	 */
	@RequestMapping(params = "method=roleCombobox")
	public void roleCombobox(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, roleService.combobox());
	}

}
