/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.demo.entity.DemoStudent;
import com.osmp.web.demo.service.DemoStudentService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 下午3:06:34
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoStudentService studentService;

	/**
	 * 跳转到列表界面
	 * 
	 * @return
	 */
	@RequestMapping("/toList")
	public String toList() {
		return "demo/studentlist";
	}

	@RequestMapping("/studentList")
	@ResponseBody
	public Map<String, Object> userList(DataGrid dg, HttpServletResponse response) {
		Pagination<DemoStudent> page = new Pagination<DemoStudent>(dg.getPage(), dg.getRows());
		studentService.getStudent(page);
		dg.setResult(page.getList());
		return dg.getMap();
	}

}
