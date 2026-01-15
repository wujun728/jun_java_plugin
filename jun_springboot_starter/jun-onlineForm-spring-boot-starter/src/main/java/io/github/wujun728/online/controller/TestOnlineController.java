package io.github.wujun728.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试控制器，用于验证应用是否正常启动
 */
@Controller
public class TestOnlineController {

    /**
     * 简单的测试接口
     */
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello, application is running!";
    }

    /**
     * 访问测试页面
     */
    @GetMapping("/test-page")
    public String testPage() {
        return "index.html";
    }
}
