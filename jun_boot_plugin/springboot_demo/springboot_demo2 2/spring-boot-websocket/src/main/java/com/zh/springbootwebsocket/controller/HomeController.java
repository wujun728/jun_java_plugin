package com.zh.springbootwebsocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootwebsocket.config.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wujun
 * @date 2019/6/21
 */
@Controller
public class HomeController {

    @Autowired
    private WebSocket webSocket;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String userName){
        return userName;
    }

    @GetMapping("/listAllUser")
    @ResponseBody
    public List<JSONObject> listAllUser(){
        return this.webSocket.listOnline();
    }

    @PostMapping("/sendSingleMsg")
    @ResponseBody
    public void sendSingleMsg(String fromUserName,String toUserName, String msg, HttpSession session){
        this.webSocket.sendP2PMsgBy2UserName(fromUserName,toUserName,msg);
    }

    @PostMapping("/sendMultiMsg")
    @ResponseBody
    public void sendMultiMsg(String fromUserName,String msg){
        this.webSocket.sendMsg(fromUserName,msg);
    }
}
