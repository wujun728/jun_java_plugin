/**  
 * @Project: erp
 * @Title: PermissionAssignmentAction.java
 * @Package com.erp.action
 * @Description: TODO:
 * @author Wujun
 * @date 2013-5-17 下午3:16:55
 * @Copyright: 2013 www.example.com Inc. All rights reserved.
 * @version V1.0  
 */
package com.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.erp.model.Role;
import com.erp.service.IPermissionAssignmentService;
import com.erp.viewModel.GridModel;
import com.erp.viewModel.Json;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

/**
 * 类功能说明 TODO: 类修改者 修改日期 修改说明
 * <p>
 * Title: PermissionAssignmentAction.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:福产流通科技有限公司
 * </p>
 * 
 * @author Wujun
 * @date 2013-5-17 下午3:16:55
 * @version V1.0
 */
// @Namespace("/permission")
// @Action(value = "permissionAssignmentAction")
@Controller
@RequestMapping("/permissionController.do")
public class PermissionController extends BaseController{
	private static final long serialVersionUID = -7653440308109010857L;
	private IPermissionAssignmentService permissionAssignmentService;

	// private Integer id;
	// private String checkedIds;
	// private Role role;

	// public Role getRole()
	// {
	// return role;
	// }
	//
	// public void setRole(Role role )
	// {
	// this.role = role;
	// }

	// public String getCheckedIds()
	// {
	// return checkedIds;
	// }
	//
	// public void setCheckedIds(String checkedIds )
	// {
	// this.checkedIds = checkedIds;
	// }
	//
	// public Integer getId()
	// {
	// return id;
	// }
	//
	// public void setId(Integer id )
	// {
	// this.id = id;
	// }

	@Autowired
	public void setPermissionAssignmentService(IPermissionAssignmentService permissionAssignmentService) {
		this.permissionAssignmentService = permissionAssignmentService;
	}

	/**
	 * 函数功能说明 TODO:按节点查询所有程式 Administrator修改者名字 2013-5-10修改日期 修改内容
	 * 
	 * @Title: findAllFunctionList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findAllFunctionList")
	public String findAllFunctionList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Integer id = null;
		if (param.get("id") != null) {
			id = Integer.valueOf(String.valueOf(param.get("id")));
		}
		OutputJson(permissionAssignmentService.findAllFunctionsList(id), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:查询所有角色 Administrator修改者名字 2013-5-20修改日期 修改内容
	 * 
	 * @Title: findAllRoleList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findAllRoleList")
	public String findAllRoleList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
//		Map<String, Object> map = searchRole();
		GridModel gridModel = new GridModel();
		gridModel.setRows(permissionAssignmentService.findAllRoleList(map, pageUtil.getPage(), pageUtil.getRows(), true));
		gridModel.setTotal(permissionAssignmentService.getCount(map));
		OutputJson(gridModel, response);
		return null;
	}

//	private Map<String, Object> searchRole() {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (null != searchValue && !"".equals(searchValue)) {
//			map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
//		}
//		return map;
//	}

	/**
	 * 函数功能说明 TODO:查询所有角色不分页 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: findAllRoleListNotPage
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findAllRoleListNotPage")
	public String findAllRoleListNotPage(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map<String, Object> map = searchRole();
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		GridModel gridModel = new GridModel();
		gridModel.setRows(permissionAssignmentService.findAllRoleList(map, null, null, false));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:有roleId获取角色权限 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: getRolePermission
	 * @Description: TODO:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=getRolePermission")
	public String getRolePermission(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Role role = new Role();
		WebUtil.MapToBean(param, role);
		OutputJson(permissionAssignmentService.getRolePermission(role.getRoleId()), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:保存角色权限 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: savePermission
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=savePermission")
	public String savePermission(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Role role = new Role();
		WebUtil.MapToBean(param, role);

		Json json = new Json();
		if (permissionAssignmentService.savePermission(role.getRoleId(), String.valueOf(param.get("checkedIds")))) {
			json.setStatus(true);
			json.setMessage("分配成功！查看已分配权限请重新登录！");
		} else {
			json.setMessage("分配失败！");
		}
		OutputJson(json, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:持久化角色 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: persistenceRole
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceRole")
	public String persistenceRole(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<Role>> map = new HashMap<String, List<Role>>();
		Map param = WebUtil.getAllParameters(request);
		Role role = new Role();
		WebUtil.MapToBean(param, role);

		map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), Role.class));
		map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), Role.class));
		map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), Role.class));

		Json json = new Json();
		if (permissionAssignmentService.persistenceRole(map)) {
			json.setStatus(true);
			json.setMessage("数据更新成功！");
		} else {
			json.setMessage("提交失败了！");
		}
		OutputJson(json, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:弹窗持久化角色 Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: persistenceRoleDlg
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceRoleDlg")
	public String persistenceRoleDlg(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Role role = new Role();
		WebUtil.MapToBean(param, role);
		OutputJson(getMessage(permissionAssignmentService.persistenceRole(role)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	@RequestMapping(params = "method=delRole")
	public String delRole(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Role role = new Role();
		WebUtil.MapToBean(param, role);
		OutputJson(getMessage(permissionAssignmentService.persistenceRole(role.getRoleId())), response);
		return null;
	}
	// public Role getModel()
	// {
	// if (null==role)
	// {
	// role =new Role();
	// }
	// return role;
	// }
}
