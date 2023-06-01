package com.chitry.log4jmonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/12/8 0008.
 */
@Controller
public class TestController {
    private Logger log = Logger.getLogger(TestController.class);

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        log.info("看看");
        log.debug("看看");
        log.error("看看");
        System.out.println("看看");
        return "看看";
    }
}
