package com.kind.springboot.core.rest;

import com.kind.springboot.common.annotation.Authorization;
import com.kind.springboot.common.annotation.CurrentUser;
import com.kind.springboot.common.config.ResultStatus;
import com.kind.springboot.common.manager.TokenManager;
import com.kind.springboot.common.token.TokenDto;
import com.kind.springboot.core.domain.UserDo;
import com.kind.springboot.core.dto.ResultMsg;
import com.kind.springboot.core.service.UserService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Function:获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出. <br/>
 * Date: 2016年8月11日 下午12:56:34 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Resource
    private UserService userServie;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public ResponseEntity<ResultMsg> login(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");
        UserDo user = userServie.getByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            /**
             * 提示用户名或密码错误
             */
            return new ResponseEntity<>(ResultMsg.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        /**
         * 生成一个token，保存用户登录状态
         */
        TokenDto model = tokenManager.createToken(user.getId());
        return new ResponseEntity<>(ResultMsg.ok(model), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),})
    public ResponseEntity<ResultMsg> logout(@CurrentUser UserDo user) {
        tokenManager.removeToken(user.getId());
        return new ResponseEntity<>(ResultMsg.ok(), HttpStatus.OK);
    }

}
