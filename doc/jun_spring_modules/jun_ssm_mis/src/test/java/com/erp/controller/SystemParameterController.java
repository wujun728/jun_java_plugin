package com.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.erp.model.Parameter;
import com.erp.service.ISystemParameterService;
import com.jun.plugin.utils.WebUtil;

//@Namespace("/systemParameter")
//@Action(value = "systemParameterAction")
@Controller
@RequestMapping("/systemParameterController.do")
public class SystemParameterController extends BaseController {
	private static final long serialVersionUID = -6666601833262807698L;
	private ISystemParameterService systemParameterService;

	@Autowired
	public void setSystemParameterService(ISystemParameterService systemParameterService) {
		this.systemParameterService = systemParameterService;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceParameter")
	public String persistenceParameter(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<Parameter>> map = new HashMap<String, List<Parameter>>();
		Map param = WebUtil.getAllParameters(request);
		map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), Parameter.class));
		map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), Parameter.class));
		map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), Parameter.class));
		OutputJson(getMessage(systemParameterService.persistenceParameter(map)),response);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=findParameterList")
	public String findParameterList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		OutputJson(systemParameterService.findParameterList(String.valueOf(param.get("type"))),response);
		return null;
	}
}
