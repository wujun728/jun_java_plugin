package com.chitry.log4jmonitor.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chitry@126.com
 * @date 2016年9月28日 上午11:36:59
 * @topic 打印log日志
 * @description 此servlet用于测试，以便看到效果
 */
@Controller
public class Sniff extends HttpServlet {

    private static Logger log = Logger.getLogger(Sniff.class);

    @RequestMapping("/sniff")
    @ResponseBody
    public void sniff(HttpServletRequest request, HttpServletResponse response) {
    }
}
