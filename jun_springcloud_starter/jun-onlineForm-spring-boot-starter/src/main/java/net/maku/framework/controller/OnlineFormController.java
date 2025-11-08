package net.maku.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 在线表单控制器
 * 提供在线表单的访问入口和基本功能
 */
@Controller
@RequestMapping("/online/form")
public class OnlineFormController {

    /**
     * 进入在线表单页面
     */
    @GetMapping
    public String index() {
        return "online/form";
    }
    
    /**
     * 获取表单配置信息
     */
    @GetMapping("/config")
    @ResponseBody
    public String getFormConfig() {
        // 这里可以返回表单的配置信息
        return "{\"success\":true,\"message\":\"获取表单配置成功\"}";
    }
    
    /**
     * 保存表单数据
     */
    @GetMapping("/save")
    @ResponseBody
    public String saveFormData() {
        // 这里可以处理表单数据的保存逻辑
        return "{\"success\":true,\"message\":\"表单数据保存成功\"}";
    }
}