package com.neo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class Usercontroller {
    
    @Resource
    private JedisCluster jedisCluster;
    
    @RequestMapping("/login")
    public @ResponseBody Object login(){
	   jedisCluster.set("zx_hello", "hi  welocme login!");
	    String value=jedisCluster.get("zx_hello");
	    System.out.println(value);
	     return value;
     }
    
    
          

}

