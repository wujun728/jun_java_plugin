package com.yzm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yzm.vo.ResponseResult;

@RestController
@RequestMapping(value = "/test")
public class TestController {

	@RequestMapping(value = "/testCross")
	public ResponseResult testCross() {
		return new ResponseResult.Builder().fail().build();
	}
}
