package com.zh.springbootswagger.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootswagger.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 * @date 2019/5/31
 */
@Api(value = "欢迎", description = "欢迎业务相关API")
@RestController
public class HelloController {

    @ApiOperation("欢迎")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", paramType = "query", value = "姓名", required = true, dataType = "String")
    })
    @GetMapping("/hello")
    public String hello(String name){
        return "Hello World " + name;
    }

    @ApiOperation("保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", paramType = "query", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", paramType = "query", value = "年龄", required = true, dataType = "Integer"),
    })
    @PostMapping("/save")
    public String save(User user){
        return JSONObject.toJSONString(user);
    }

}
