/*   
 * Project: 基础组件 
 * FileName: UserController.java
 * Company: Chengdu osmp Technology Co.,Ltd
 * version: V1.0
 */
package com.osmp.web.system.sermapping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.sermapping.entity.SerMapping;
import com.osmp.web.system.sermapping.service.SerMappingService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description: DataServiceMapping管理
 * 
 * @author: chenbenhui
 * @date: 2014年12月1日 上午2:23:30
 */
@Controller
@RequestMapping("/sermapping")
public class SerMappingController {
	
	@Autowired
	private SerMappingService serMappingService;

	@RequestMapping("/toList")
	public String toList() {
		return "system/sermapping/list";
	}
	
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
		ModelAndView mv = new ModelAndView("system/sermapping/add");
		SerMapping serMapping = new SerMapping();
        if ( !"".equals(id) ) {
            serMapping.setId(Integer.parseInt(id));
            serMapping = serMappingService.getSerMapping(serMapping);
        }
        mv.addObject("serMapping", serMapping);
        return mv;
	}
	
	@RequestMapping("/serMappingList")
	@ResponseBody
	public Map<String, Object> serMappingList(DataGrid dg, HttpServletResponse response){
		Pagination<SerMapping> p = new Pagination<SerMapping>(dg.getPage(), dg.getRows());
		List<SerMapping> list = serMappingService.getList(p,"");
		dg.setResult(list);
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}
	
	@RequestMapping("/addSerMapping")
	@ResponseBody
	public Map<String,Object> addSerMapping(SerMapping serMapping){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			serMappingService.addSerMapping(serMapping);
			map.put("success",true);
			map.put("msg","添加成功");
		}catch(Exception e){
			e.printStackTrace();
			map.put("success",false);
			map.put("msg","添加失败");
		}
		return map;
	}
	
	
	@RequestMapping("/updateSerMapping")
	@ResponseBody
	public Map<String,Object> updateSerMapping(SerMapping serMapping){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			serMappingService.updateSerMapping(serMapping);
			map.put("success",true);
			map.put("msg","修改成功");
		}catch(Exception e){
			e.printStackTrace();
			map.put("success",false);
			map.put("msg","修改失败");
		}
		return map;
	}

	@RequestMapping("/deleteSerMapping")
	@ResponseBody
	public Map<String, Object> deleteSerMapping(SerMapping serMapping) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			serMappingService.deleteSerMapping(serMapping);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/bundles")
	@ResponseBody
	public List<String> getBundles(){
		List<String> list = serMappingService.getBundels();
		return list;
	}
	
	@RequestMapping("/versions")
	@ResponseBody
	public List<String> getVerions(){
		return serMappingService.getVersions();
	}
	
	@RequestMapping("/services")
	@ResponseBody
	public List<String> getServices(){
		return serMappingService.getServices();
	}
}
