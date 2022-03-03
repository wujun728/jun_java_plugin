/**  
persistenceFunctionDig* @Project: erp
 * @Title: FunctionAction.java
 * @Package com.erp.action
 * @Description: TODO:
 * @author Wujun
 * @date 2013-5-9 下午1:50:56
 * @Copyright: 2013 www.example.com Inc. All rights reserved.
 * @version V1.0  
 */
package com.erp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.erp.model.Permission;
import com.erp.service.IFunctionService;
import com.erp.viewModel.Json;
import com.erp.viewModel.PermissionModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.StringUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.DateEditor;

//import com.opensymphony.xwork2.ModelDriven;

/**
 * 类功能说明 TODO:程式管理action 类修改者 修改日期 修改说明
 * <p>
 * Title: FunctionAction.java
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
 * @date 2013-5-9 下午1:50:56
 * @version V1.0
 */
// @Namespace("/function")
// @Action(value = "functionAction")
@Controller
@RequestMapping("/functionController.do")
public class FunctionController extends BaseController {
	private static final Logger logger = Logger.getLogger(FunctionController.class);
	private static final long serialVersionUID = -834064728613242979L;

	@Autowired(required = false)
	@Qualifier("functionService")
	private IFunctionService functionService;

	@Autowired(required = false)
	@Qualifier("permission")
	private Permission permission;

	// private Integer id;

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/**
	 * 函数功能说明 TODO:持久化程式实体 Administrator修改者名字 2013-5-10修改日期 修改内容
	 * 
	 * @Title: persistenceFunction
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceFunction")
	public String persistenceFunction(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Json json = new Json();
		if (functionService.persistenceFunction(JSON.parseArray("updated", Permission.class))) {
			logger.debug("持久化信息！");
			json.setStatus(true);
			json.setMessage(Constants.POST_DATA_SUCCESS);
		} else {
			json.setMessage(Constants.POST_DATA_FAIL);
		}
		OutputJson(json, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:弹出框编辑function Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: persistenceFunctionDig
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceFunctionDig")
	// public String persistenceFunctionDig(@ModelAttribute Permission permission,Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	public String persistenceFunctionDig(Model model, @ModelAttribute("permission") PermissionModel permission,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Permission p = new Permission();
		WebUtil.copyBean3(permission, p);
		Map param = WebUtil.getAllParameters(request);
		if(param.get("cp")!=null && "1".equals(String.valueOf(param.get("cp")))){
			p.setPermissionId(0);
		}
		OutputJson(getMessage(functionService.persistenceFunction(p)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	@RequestMapping(value = "/user/add", method = { RequestMethod.POST })
	public String add(Model model, @ModelAttribute("permission") @Validated Permission permission, BindingResult result) {

		// 如果有验证错误 返回到form页面
		if (result.hasErrors()) {
			// model.addAttribute(Constants.COMMAND, command);
			// return toAdd(model);
		}
		// userService.save(command);
		return "redirect:/user/success";
	}

	@InitBinder("permission")
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(PermissionModel.class, new DateEditor());
	}

	// @InitBinder
	// protected void initBinder(WebDataBinder binder){
	// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd dd:HH:ss");
	// sdf.setLenient(false);
	// binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,true));
	// }

	// @InitBinder("user")
	// public void initBinder2(WebDataBinder binder) {
	// // binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	// binder.setFieldDefaultPrefix("user.");//别名前缀
	// }
	// @InitBinder("student")
	// public void initBinder1(WebDataBinder binder) {
	// // binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	// binder.setFieldDefaultPrefix("student.");//别名前缀
	// }
	// @RequestMapping(value="/new")
	// public String _new(ModelMap model, Student student,@ModelAttribute("bb")User user) throws Exception {
	// //model.addAttribute("student",);
	// System.out.println(user.getId());
	// System.out.println(student.getName());
	// System.out.println(user.getName());
	// return "/user/form_include";
	// }
	/**
	 * 函数功能说明 TODO:删除程式 Administrator修改者名字 2013-5-10修改日期 修改内容
	 * 
	 * @Title: delFunction
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delFunction")
	public String delFunction(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Json json = new Json();
		Integer id = Integer.valueOf(StringUtil.decodeToUtf(request.getParameter("id")));
		if (functionService.delFunction(id)) {
			json.setStatus(true);
			json.setMessage(Constants.POST_DATA_SUCCESS);
		} else {
			json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
		}
		OutputJson(json, response);
		return null;
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
		String id = StringUtil.decodeToUtf(request.getParameter("id"));
		OutputJson(functionService.findAllFunctionList(id), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:查询所有程式 Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: findAllFunctionLists
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findAllFunctionLists")
	public String findAllFunctionLists(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(functionService.findAllFunctionList(), response);
		return null;
	}

	public Permission getModel() {
		if (null == permission) {
			permission = new Permission();
		}
		return permission;
	}
}
