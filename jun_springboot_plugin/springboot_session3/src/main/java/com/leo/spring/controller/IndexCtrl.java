package com.leo.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * IndexCtrl
 *
 * @author Wujun
 * @date 2017-1-11
 */
@Controller
@RequestMapping("/")
public class IndexCtrl {

    @Resource
    private HttpSession session;

    @Resource
    private HttpServletRequest request;

    @RequestMapping("")
    public String index(Map<String,Object> map){
        map.put("str","welcome to spring boot!");
        map.put("sessionId",session.getId());
        map.put("url", request.getRequestURL());
        return "index";
    }

    @RequestMapping("ws")
    public String ws(){
        return "websocket";
    }

    @RequestMapping("home")
    public String home(){
        return "home";
    }
}
