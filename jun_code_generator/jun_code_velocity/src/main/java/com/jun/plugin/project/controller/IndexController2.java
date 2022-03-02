package com.jun.plugin.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.jun.plugin.framework.web.controller.BaseController;

/**
 * 首页 业务处理
 * 
 * @author ruoyi
 */
@Controller
public class IndexController2 extends BaseController
{

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        return "index";
    }
}
