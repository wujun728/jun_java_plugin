package com.cosmoplat.controller;

import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.entity.sys.SysUser;
import com.cosmoplat.service.sys.HttpSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author project
 * @since 2020-01-08
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private HttpSessionService httpSession;

    @PostMapping("/login")
    public DataResult login(@RequestBody @Valid SysUser sysUser) {
        //生成token
        String token = httpSession.createTokenAndUser(sysUser, null, null);
        return DataResult.success(token);
    }
}
