package com.jun.plugin.rbac.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.rbac.shiro.common.R;

/**
 * <p>
 * 测试Controller
 * </p>
 *
 * @package: com.xkcoding.rbac.shiro.controller
 * @description: 测试Controller
 * @author: yangkai.shen
 * @date: Created in 2019-03-21 16:13
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public R test() {
        return R.success();
    }
}
