package com.mycompany.myproject.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("client/")
public class ClientInfoController {


    @RequestMapping(value = "infolist", method=RequestMethod.GET)
    @ResponseBody
    public Map<String , String> getHeader(HttpServletRequest httpServletRequest){

        Enumeration headerNames = httpServletRequest.getHeaderNames();

        HashMap<String,String> hashMap = new HashMap<String,String>();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            hashMap.put(key, value);
        }

        return hashMap;
    }

}
