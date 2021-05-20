package cn.classg.controller;


import cn.classg.common.utils.R;
import cn.classg.entity.User;
import cn.classg.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(description = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查询所有用户")
    @GetMapping("/queryAllUser")
    public R queryAllUser() {
        return R.ok().data(userService.queryAllUser());
    }


    @ApiOperation("查询所有用户（分页）")
    @GetMapping("/queryAllUserPage")
    public R queryAllUserPage(@RequestParam Integer page, @RequestParam Integer limit) {
        PageInfo<User> queryPageInfo = userService.queryAllUserPage(page, limit);
        return R.ok().data(queryPageInfo.getList()).count(queryPageInfo.getTotal());
    }


    @ApiOperation("根据id查询用户")
    @GetMapping("/queryUserById")
    public R queryUserById(@RequestParam String id) {
        User user = userService.queryUserById(id);
        return R.ok().data(user);
    }


    @ApiOperation("添加用户")
    @PostMapping("/insertUser")
    public R insertUser(@RequestParam("userString") String userString) {
        User user = JSONObject.parseObject(userString, User.class);
        Integer result = userService.insertUser(user);
        if (result > 0) {
            return R.ok();
        }
        return R.error().msg("添加用户失败！");
    }


    @ApiOperation("更新用户")
    @PostMapping("/updateUser")
    public R updateUser(@RequestParam String userString) {
        User user = JSONObject.parseObject(userString, User.class);
        Integer result = userService.updateUser(user);
        if (result > 0) {
            return R.ok();
        }
        return R.error().msg("更新用户失败！");
    }


    @ApiOperation("根据id删除用户")
    @PostMapping("/deleteUserById")
    public R deleteUserById(@RequestParam("id") String id) {
        Integer result = userService.deleteUserById(id);
        if (result > 0) {
            return R.ok();
        }
        return R.error().msg("删除用户失败！");
    }

    @ApiOperation("批量删除用户")
    @PostMapping("/deleteUserByIds")
    public R deleteUserByIds(@RequestParam("users") String users) {
        List<User> userList = JSONObject.parseArray(users, User.class);
        Integer result = userService.deleteUserByIds(userList);
        if (result > 0) {
            return R.ok();
        }
        return R.error().msg("删除用户失败！");
    }

}
