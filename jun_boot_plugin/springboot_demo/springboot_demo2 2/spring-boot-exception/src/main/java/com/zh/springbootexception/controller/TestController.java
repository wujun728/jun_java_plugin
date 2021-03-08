package com.zh.springbootexception.controller;

import com.zh.springbootexception.dto.Result;
import com.zh.springbootexception.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @date 2019/6/5
 */
@RestController
@Validated
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public Result test(@NotNull(message = "index不能为空") Integer index){
        return this.testService.test(index);
    }
}
