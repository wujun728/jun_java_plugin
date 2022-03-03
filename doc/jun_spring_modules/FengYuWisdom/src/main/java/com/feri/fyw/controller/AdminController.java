package com.feri.fyw.controller;

import com.feri.fyw.dto.AdminDto;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.service.intf.AdminService;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:42
 */
@Controller
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    private AdminService service;

    //新增
    @RequestMapping("save.do")
    @ResponseBody
    public R save(Admin admin){
        return service.save(admin);
    }
    //查询
    @RequestMapping("all.do")
    @ResponseBody
    public PageBean all(){
        return service.queryList();
    }
    //登陆
    @RequestMapping("login.do")
    @ResponseBody
    public R login(AdminDto dto, HttpSession session){
        return service.login(dto,session);
    }
    //注销
    @RequestMapping("loginout.do")
    public String logionOut(HttpSession session){
        service.loginout(session);
        return "/login.html";
    }


}
