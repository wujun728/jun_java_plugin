package com.baomidou.springwind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;

/**
 * <p>
 * 监控
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController {

	/**
	 * 实时监控
	 */
	@Permission(action = Action.Skip)
	@RequestMapping("/realTime")
	public String realTime(Model model) {
		
		return "/monitor/realTime";
	}

}
