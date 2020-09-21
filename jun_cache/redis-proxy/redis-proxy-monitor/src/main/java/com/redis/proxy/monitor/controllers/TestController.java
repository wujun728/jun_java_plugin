/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.monitor.controllers;

import com.redis.proxy.monitor.vo.User;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author zhanggaofeng
 */
@Controller
public class TestController {

        private final Logger logger = LoggerFactory.getLogger(TestController.class);

        @RequestMapping("/")
        public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World!") String name, Model model) {
                return "index";
        }
}
