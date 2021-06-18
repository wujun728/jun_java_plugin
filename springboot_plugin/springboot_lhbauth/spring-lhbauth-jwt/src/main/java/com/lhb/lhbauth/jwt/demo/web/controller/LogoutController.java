package com.lhb.lhbauth.jwt.demo.web.controller;

import com.lhb.lhbauth.jwt.demo.common.ResultModel;
import com.lhb.lhbauth.jwt.demo.common.SWCodeEnum;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Wujun
 * @description
 * @date 2018/12/27 0027 11:09
 */
@RestController
public class LogoutController {

    @Resource
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/myLogout")
    public ResultModel wuJinLogout(@RequestParam("token")String accessToken){
        if (consumerTokenServices.revokeToken(accessToken)) {
            return ResultModel.ok();
        }
        return ResultModel.fail(SWCodeEnum.CODE_20004);
    }


}
