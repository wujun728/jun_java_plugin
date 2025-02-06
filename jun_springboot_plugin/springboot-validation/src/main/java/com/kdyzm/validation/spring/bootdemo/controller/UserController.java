package com.kdyzm.validation.spring.bootdemo.controller;

import com.kdyzm.validation.spring.bootdemo.group.ValidEmail;
import com.kdyzm.validation.spring.bootdemo.group.ValidMobile;
import com.kdyzm.validation.spring.bootdemo.group.ValidUserName;
import com.kdyzm.validation.spring.bootdemo.model.UserModel;
import com.kdyzm.validation.spring.bootdemo.model.WrapperResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping("/user")
@Validated
public class UserController {

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping
    public WrapperResult<UserModel> findUser(@NotNull(message = "用户id不能为空")
                                                 @RequestParam(value = "id",required = false)
                                                         Integer id) {
        return WrapperResult.success(new UserModel());
    }

    /**
     * 手机号注册
     *
     * @param userModel
     * @return
     */
    @PostMapping("/mobile-regist")
    public WrapperResult<Boolean> mobileRegit(@Validated(ValidMobile.class) @RequestBody UserModel userModel) {
        return WrapperResult.success(true);
    }

    /**
     * 邮箱注册
     *
     * @param userModel
     * @return
     */
    @PostMapping("/email-regist")
    public WrapperResult<Boolean> emailRegist(@Validated(ValidEmail.class) @RequestBody UserModel userModel) {
        return WrapperResult.success(true);
    }

    /**
     * 用户名注册
     *
     * @param userModel
     * @return
     */
    @PostMapping("/username-regist")
    public WrapperResult<Boolean> userNameRegist(@Validated(ValidUserName.class) @RequestBody UserModel userModel) {
        return WrapperResult.success(true);
    }


}
