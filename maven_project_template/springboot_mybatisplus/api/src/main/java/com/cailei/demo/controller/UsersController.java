package com.cailei.demo.controller;

import com.cailei.demo.service.UsersService;
import com.cailei.demo.vo.ResultVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：蔡磊
 * @date ：2022/10/20 16:57
 * @description：Controller层
 */

@Api(value = "提供操作用户接口", tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UsersController {

    @Resource
    private UsersService usersService;

    @ApiOperation("查询用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "userId", value = "用户id大于", required = true,example = "5"),
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户名称包含", required = true,example = "i")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 404, message = "请求失败")})
    @GetMapping("/getUser")
    public ResultVo getUser(Integer userId, String username) {
        ResultVo resultVo = usersService.queryUsers(userId,username);
        return resultVo;
    }
}

