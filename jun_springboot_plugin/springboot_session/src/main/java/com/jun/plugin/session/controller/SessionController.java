package com.jun.plugin.session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * session测试
 * </p>
 *
 * @author MrWen
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/save/{message}")
    public String save(@PathVariable("message") String message, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("test", message);
        return "success";
    }


    @GetMapping("/detail")
    public String detail(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("test");
    }

}
