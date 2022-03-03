package com.feri.fyw.controller;

import com.feri.fyw.service.intf.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 10:01
 */
@Controller
public class CaptchaController {

    @Autowired
    private CaptchaService service;
    //生成验证码
    @GetMapping("/api/captch/create.do")
    public void create(HttpSession session, HttpServletResponse response) throws IOException {
        service.createImg(session, response);
    }
    //主页跳转
    @GetMapping("/")
    public String index(){
        return "/login.html";
    }
}
