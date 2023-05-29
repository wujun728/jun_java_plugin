package com.jun.plugin.exception.handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.exception.handler.constant.Status;
import com.jun.plugin.exception.handler.exception.JsonException;
import com.jun.plugin.exception.handler.exception.PageException;
import com.jun.plugin.exception.handler.model.ApiResponse;

/**
 * <p>
 * 测试Controller
 * </p>
 *
 * @package: com.xkcoding.exception.handler.controller
 * @description: 测试Controller
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 8:49 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
public class TestController {

	@GetMapping("/json")
	@ResponseBody
	public ApiResponse jsonException() {
		throw new JsonException(Status.UNKNOWN_ERROR);
	}

	@GetMapping("/page")
	public ModelAndView pageException() {
		throw new PageException(Status.UNKNOWN_ERROR);
	}
}
