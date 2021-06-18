package com.thinkingcao.silence.ssm.web.controller;

import com.thinkingcao.silence.ssm.entity.User;
import com.thinkingcao.silence.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userService.listAll());
        return "user/list";
    }

    @RequestMapping("/input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("user", userService.get(id));
        }
        return "user/input";
    }

    @RequestMapping("/delete")
    public String delete(Long id) {
        if (id != null) {
            userService.delete(id);
        }
        return "redirect:/user/list";
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(User user) {
        if (user.getId() == null) {
            userService.save(user);
        } else {
            userService.update(user);
        }
        return "redirect:/user/list";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}