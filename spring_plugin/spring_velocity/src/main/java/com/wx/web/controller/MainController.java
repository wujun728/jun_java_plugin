package com.wx.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wx.common.exception.BusinessException;

/**
 * @author Wujun
 *
 */
@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/index")
    public String index() throws BusinessException {
        log.debug("# 进入首页......");
        return "index";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error403() {
        return "/common/403";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "/common/404";
    }

}
