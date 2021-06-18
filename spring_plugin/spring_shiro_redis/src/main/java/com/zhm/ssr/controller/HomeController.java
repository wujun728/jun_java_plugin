package com.zhm.ssr.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhm on 2015/7/10.
 */
@Controller
public class HomeController {
    @RequestMapping(value="/")
    public String root(){
        return "redirect:/home/main.html";
    }
    @RequestMapping("/home/main")
    public String index(){
        return "home";
    }

    @RequestMapping("/nolimit")
    public String nolimit(HttpServletRequest request,HttpServletResponse respose) throws IOException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            respose.setContentType("text/html; utf-8");
            respose.getWriter().write("无权限操作");
            return null;
        }else{
            return "nolimit";
        }
    }
}
