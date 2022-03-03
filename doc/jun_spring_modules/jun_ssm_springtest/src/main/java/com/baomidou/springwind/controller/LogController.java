package com.baomidou.springwind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.SysLog;
import com.baomidou.springwind.service.ISysLogService;

/**
 * <p>
 * 操作日志
 * </p>
 *
 *
 * @Author hubin
 * @Date 2016-05-09
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

	@Autowired
	private ISysLogService sysLogService;

	@Permission("4001")
	@RequestMapping("/list")
	public String list(Model model) {
		return "/log/list";
	}

	@ResponseBody
	@Permission("4001")
	@RequestMapping("/getLogList")
	public String getUserList() {
		Page<SysLog> page = getPage();
		return jsonPage(sysLogService.selectPage(page, null));
	}

	@ResponseBody
	@Permission("4001")
	@RequestMapping("/delete/{id}")
	public String delUser(@PathVariable Long id) {
		sysLogService.deleteById(id);
		return Boolean.TRUE.toString();
	}
}
