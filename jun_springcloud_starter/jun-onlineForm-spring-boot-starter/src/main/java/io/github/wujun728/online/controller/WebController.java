package io.github.wujun728.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 静态页面控制器
 * 用于提供在线表单开发工具的前端页面访问入口
 */
@Controller
@RequestMapping("/online")
public class WebController {
    
    /**
     * 在线表单开发工具首页
     */
    @GetMapping("/index")
    public String index() {
        return "redirect:/online/index.html";
    }
    
    /**
     * 表单管理页面
     */
    @GetMapping("/form")
    public String form() {
        return "redirect:/online/form.html";
    }
    
    /**
     * 提供一个简单的欢迎页面
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "redirect:/online/index.html";
    }
}