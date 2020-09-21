/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.demo.entity.DemoTeacher;
import com.osmp.web.demo.service.DemoTeacherService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 下午3:06:34
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private DemoTeacherService teacherService;

	/**
	 * 跳转到列表界面
	 * 
	 * @return
	 */
	@RequestMapping("/toList")
	public String toList() {
		return "teacher/list";
	}

	/**
	 * 跳转到列表界面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "teacher/teacherAdd";
	}

	@RequestMapping("/teacherlist")
	@ResponseBody
	public Map<String, Object> userList(DataGrid dg, HttpServletResponse response) {
		Pagination<DemoTeacher> page = new Pagination<DemoTeacher>(dg.getPage(), dg.getRows());
		teacherService.getTeacher(page);
		dg.setResult(page.getList());
		return dg.getMap();
	}

	@RequestMapping("/addTeacher")
	@ResponseBody
	public Map<String, Object> addTeacher(DemoTeacher teacher) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			teacherService.insert(teacher);
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

}
