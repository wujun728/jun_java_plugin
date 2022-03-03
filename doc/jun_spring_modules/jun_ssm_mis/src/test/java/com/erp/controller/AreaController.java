package com.erp.controller;

//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.City;
import com.erp.service.IAreaService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;

//@Namespace("/area")
//@Action(value = "areaAction")
@Controller
@RequestMapping("/areaController.do")
public class AreaController extends BaseController {
	private static final long serialVersionUID = 5060080266833835121L;
	private IAreaService areaService;
	private City city;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Autowired
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	@RequestMapping(params = "method=findCities")
	public String findCities(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(areaService.findCities(), response);
		return null;
	}

	@RequestMapping(params = "method=findProvinces")
	public String findProvinces(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(areaService.findProvinces(), response);
		return null;
	}

	@RequestMapping(params = "method=addCities")
	public String addCities(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, getModel());
		OutputJson(getMessage(areaService.addCities(getModel())), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	public City getModel() {
		if (null == city) {
			city = new City();
		}
		return city;
	}
}
