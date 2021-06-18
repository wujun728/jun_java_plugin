package com.jun.plugin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.dto.ResponseData;

@RestController("indexController")
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/ok")
    public ResponseData index() {
        return ResponseData.ok();
    }

}
