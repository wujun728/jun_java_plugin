package com.zh.springbootmongodb.controller;

import cn.hutool.core.util.RandomUtil;
import com.zh.springbootmongodb.entity.dto.Result;
import com.zh.springbootmongodb.entity.model.User;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Result hello(@NotBlank(message = "姓名不能为空") String name){
        return Result.genSuccessResult("Hello World " + name);
    }

    @PostMapping("save")
    public Result save(@Valid User user, BindingResult br) throws BindException {
        if (br.hasErrors()){
            throw new BindException(br);
        }
        user.setId(RandomUtil.randomInt(100));
        return Result.genSuccessResult(user);
    }
}
