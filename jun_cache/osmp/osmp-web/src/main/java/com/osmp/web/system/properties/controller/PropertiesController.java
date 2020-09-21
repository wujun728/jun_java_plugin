package com.osmp.web.system.properties.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.properties.entity.Properties;
import com.osmp.web.system.properties.service.PropertiesService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description:数据字典控制器
 * 
 * @author: wangkaiping
 * @date: 2014年11月14日 下午2:33:21
 */
@Controller
@RequestMapping("/properties")
public class PropertiesController {

	@Autowired
	private PropertiesService proService;

	@RequestMapping("/toList")
	public String list() {
		return "system/properties/propertiesList";
	}

	@RequestMapping("/toEdit")
	public ModelAndView toEdit(Properties pro) {
		ModelAndView mv = new ModelAndView("system/properties/propertiesEdit");
		if (pro != null && null != pro.getId()) {
			mv.addObject("pro", proService.getProperties(pro));
		}
		return mv;
	}

	@RequestMapping("/proList")
	@ResponseBody
	public Map<String, Object> proList(DataGrid dg) {
		Pagination<Properties> dict = new Pagination<Properties>(dg.getPage(), dg.getRows());
		List<Properties> list = proService.getProByPage(dict);
		dg.setTotal(dict.getTotal());
		dg.setResult(list);
		return dg.getMap();
	}

	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(Properties pro) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null != pro && pro.getId().equals("")) {
				pro.setId(UUID.randomUUID().toString());
				proService.insert(pro);
			} else {
				proService.update(pro);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	/*******
	 * 删除资源信息
	 * 
	 * @param pro
	 * @return
	 */
	@RequestMapping("/deletePro")
	@ResponseBody
	public Map<String, Object> deletePro(Properties pro) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			proService.deletePro(pro);
			map.put("success", true);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "添加失败");
		}

		return map;
	}

}
