package com.osmp.web.system.interceptors.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.system.interceptors.entity.Interceptor;
import com.osmp.web.system.interceptors.service.InterceptorService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

@Controller
@RequestMapping("/interceptors")
public class InterceptorController {

	@Autowired
	InterceptorService interceptorService;
	
	@RequestMapping("/toList")
	public String toList() {
		return "system/interceptor/list";
	}
	
	@RequestMapping("/interceptorList")
	@ResponseBody
	public Map<String, Object> serMappingList(DataGrid dg, HttpServletResponse response){
		Pagination<Interceptor> p = new Pagination<Interceptor>(dg.getPage(), dg.getRows());
		List<Interceptor> list = interceptorService.getList(p,"");
		dg.setResult(list);
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}
}
