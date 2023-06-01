package com.soukuan.controller;

import com.soukuan.domain.Test;
import com.soukuan.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* Title
* Author xiebiao@wshifu.com
* DateTime  2018-04-21.
* Version V1.0.0
*/
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping
    public void test() {
        Test test = new Test();
        testService.test(test);
    }
}
