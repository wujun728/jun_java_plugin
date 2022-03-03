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
import com.erp.model.Users;
import com.erp.service.IUserService;
import com.erp.viewModel.GridModel;
import com.erp.viewModel.Json;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/user")
//@Action(value = "userAction")
@Controller
@RequestMapping("/userController.do")
public class UserController extends BaseController {
	private static final long serialVersionUID = -8188592660918385632L;
	private IUserService userService;

	// private String isCheckedIds;
	// private Users users;

	// public Users getUsers()
	// {
	// return users;
	// }
	//
	// public void setUsers(Users users )
	// {
	// this.users = users;
	// }

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	// public String getIsCheckedIds()
	// {
	// return isCheckedIds;
	// }
	//
	// public void setIsCheckedIds(String isCheckedIds )
	// {
	// this.isCheckedIds = isCheckedIds;
	// }
	/**
	 * 函数功能说明 TODO:查询所有用户 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: findAllUserList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findAllUserList")
	public String findAllUserList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		GridModel gridModel = new GridModel();
		gridModel.setRows(userService.findAllUserList(map, pageUtil));
		gridModel.setTotal(userService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:持久化用户 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: persistenceUsers
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceUsers")
	public String persistenceUsers(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<Users>> map = new HashMap<String, List<Users>>();
		Map param = WebUtil.getAllParameters(request);
		Users users = new Users();
		WebUtil.MapToBean(param, users);
		map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), Users.class));
		map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), Users.class));
		map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), Users.class));
		Json json = new Json();
		if (userService.persistenceUsers(map)) {
			json.setStatus(true);
			json.setMessage("数据更新成功！");
		} else {
			json.setMessage("提交失败了！");
		}
		OutputJson(json, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:持久化用户弹窗模式 Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: persistenceUsersDig
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceUsersDig")
	public String persistenceUsersDig(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Users users = new Users();
		WebUtil.MapToBean(param, users);
		OutputJson(getMessage(userService.persistenceUsers(users)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:删除users Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: delUsers
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delUsers")
	public String delUsers(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Users users = new Users();
		WebUtil.MapToBean(param, users);
		OutputJson(getMessage(userService.delUsers(users.getUserId())), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:查询用户拥有角色 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: findUsersRolesList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findUsersRolesList")
	public String findUsersRolesList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Users users = new Users();
		WebUtil.MapToBean(param, users);
		OutputJson(userService.findUsersRolesList(users.getUserId()), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:保存用户分配的角色 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: saveUserRoles
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=saveUserRoles")
	public String saveUserRoles(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Users users = new Users();
		WebUtil.MapToBean(param, users);
		Json json = new Json();
		if (userService.saveUserRoles(users.getUserId(), String.valueOf(param.get("isCheckedIds")))) {
			json.setStatus(true);
			json.setMessage("数据更新成功！");
		} else {
			json.setMessage("提交失败了！");
		}
		OutputJson(json, response);
		return null;
	}

	// public Users getModel()
	// {
	// if (null==users)
	// {
	// users =new Users();
	// }
	// return users;
	// }
}
