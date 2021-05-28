package cn.kiwipeach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Create Date: 2017/11/06
 * Description: 测试SpringBoot热部署控制器
 *
 * @author Wujun
 */
@Controller
public class HotBootControl {

    @GetMapping("/welcome4")
    public String toWelcomePage3(){
        System.out.println("number is seven");
        return "welcome";
    }
}
