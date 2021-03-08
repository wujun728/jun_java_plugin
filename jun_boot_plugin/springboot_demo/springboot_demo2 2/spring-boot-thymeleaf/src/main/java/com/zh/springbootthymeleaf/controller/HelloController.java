package com.zh.springbootthymeleaf.controller;

import com.zh.springbootthymeleaf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@Controller
public class HelloController {

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("user",new User(1,"张三",27));
        return "index";
    }
}
