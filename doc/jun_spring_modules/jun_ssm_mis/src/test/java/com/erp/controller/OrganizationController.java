package com.erp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.Organization;
import com.erp.service.IOrganizationService;
import com.erp.viewModel.Json;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;

/**
 * 类功能说明 TODO:组织action 类修改者 修改日期 修改说明
 * <p>
 * Title: OrganizationAction.java
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
 * @date 2013-5-29 上午11:20:45
 * @version V1.0
 */

// @Namespace("/orgz")
// @Action(value = "organizationAction")
@Controller
@RequestMapping("/organizationController.do")
public class OrganizationController extends BaseController {
	private static final long serialVersionUID = -4604242185439314975L;
	private IOrganizationService organizationService;

	// private Integer id;
	// private Organization organization;
	// public Integer getId()
	// {
	// return id;
	// }
	//
	// public void setId(Integer id )
	// {
	// this.id = id;
	// }
	//
	// public Organization getOrganization()
	// {
	// return organization;
	// }
	//
	// public void setOrganization(Organization organization )
	// {
	// this.organization = organization;
	// }

	@Autowired
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	/**
	 * 函数功能说明 TODO:查询所有组织 Administrator修改者名字 2013-5-29修改日期 修改内容
	 * 
	 * @Title: findOrganizationList
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=findOrganizationList")
	public String findOrganizationList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(organizationService.findOrganizationList(), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:按节点查询所有组织 Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: findOrganizationListTreeGrid
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=findOrganizationListTreeGrid")
	public String findOrganizationListTreeGrid(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Integer id = null;
		if (param.get("id") != null) {
			id = Integer.valueOf(String.valueOf(param.get("id")));
		}
		OutputJson(organizationService.findOrganizationList(id), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:持久化组织 Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: persistenceOrganization
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceOrganization")
	public String persistenceOrganization(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Organization organization = new Organization();
		WebUtil.MapToBean(param, organization);
		OutputJson(getMessage(organizationService.persistenceOrganization(organization)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:删除Organization Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: delOrganization
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=delOrganization")
	public String delOrganization(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Json json = new Json();
		if (organizationService.delOrganization(Integer.valueOf(String.valueOf(param.get("id"))))) {
			json.setStatus(true);
			json.setMessage(Constants.POST_DATA_SUCCESS);
		} else {
			json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
		}
		OutputJson(json, response);
		return null;
	}
	// public Organization getModel()
	// {
	// if(null==organization){
	// organization=new Organization();
	// }
	// return organization;
	// }
}
