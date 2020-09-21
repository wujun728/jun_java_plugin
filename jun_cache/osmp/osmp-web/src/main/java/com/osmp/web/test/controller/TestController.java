/*   
 * Project: OSMP
 * FileName: RoleService.java
 * version: V1.0
 */
package com.osmp.web.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.test.dao.TestMapper;
import com.osmp.web.test.entity.Test;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestMapper mapper;

	@RequestMapping
	public void test() {
		System.out.println("--test--");
	}

	/**
	 * 跳转到列表界面
	 * 
	 * @return
	 */
	@RequestMapping("/toList")
	public String list() {
		return "test/list";
	}

	@RequestMapping("/toAdd")
	public String toAdd() {
		return "test/add";
	}

	@RequestMapping("/testList")
	@ResponseBody
	public Map<String, Object> errorLogList(DataGrid dg,
			HttpServletResponse response) {
		Pagination<Test> p = new Pagination<Test>(dg.getPage(), dg.getRows());
		dg.setResult(mapper.selectByPage(p, Test.class, ""));
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}

	@RequestMapping("/addTest")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(Test tes) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			mapper.insert(tes);
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
	
	@RequestMapping("/testDel")
	@ResponseBody
	public Map<String, Object> testDel(Test p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			mapper.delete(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

}
