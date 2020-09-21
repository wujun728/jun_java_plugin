package com.osmp.web.system.services.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.system.services.entity.Service;
import com.osmp.web.system.services.service.ServiceService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

@Controller
@RequestMapping("/services")
public class ServiceController {

	@Autowired
	private ServiceService serviceService;
	
	@RequestMapping("/toList")
	public String toList() {
		return "system/services/list";
	}
	
	@RequestMapping("/serviceList")
	@ResponseBody
	public Map<String, Object> serMappingList(DataGrid dg, HttpServletResponse response){
		Pagination<Service> p = new Pagination<Service>(dg.getPage(), dg.getRows());
		List<Service> list = serviceService.getList(p,"");
		dg.setResult(list);
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}
}
