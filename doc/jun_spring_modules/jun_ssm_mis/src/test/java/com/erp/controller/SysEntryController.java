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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.SysField;
import com.erp.service.ISysentryService;
import com.erp.viewModel.GridModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

@Controller
@RequestMapping("/sysentryController.do")
public class SysEntryController extends BaseController{
	private static final long serialVersionUID = -7653440308109010857L;
	
	@Autowired
	private ISysentryService SysentryService;


	@RequestMapping(params = "method=findAllSysEntry")
	public String findAllSysEntryList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
//		Map<String, Object> map = searchRole();
		GridModel gridModel = new GridModel();
		gridModel.setRows(SysentryService.findAllSysEntryList(map, pageUtil.getPage(), pageUtil.getRows(), true));
		gridModel.setTotal(SysentryService.getCount(map));
		OutputJson(gridModel, response);
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=getEntryFields")
	public String getRolePermission(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		SysField s = new SysField();
		WebUtil.MapToBean(param, s);
		OutputJson(SysentryService.getEntryFields(String.valueOf(s.getFieldEntityId())), response);
		return null;
	}
}
