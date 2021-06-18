package cn.kiwipeach.controller;

import cn.kiwipeach.beans.Apple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create Date: 2017/11/05
 * Description: 页面跳转控制器
 *
 * @author Wujun
 */
@Controller
public class PageController {

    @Autowired
    private Apple apple;


    @RequestMapping(value = "toHelloPage",method = {RequestMethod.GET})
    public String toHelloPage(
            @RequestParam(value = "name",defaultValue = "苹果-默认",required = false)String name,
            Model model
    ){
        if (apple!=null){
            apple.setName(name);
        }
        model.addAttribute("apple",apple);
        return "spring-boot";
    }
}
