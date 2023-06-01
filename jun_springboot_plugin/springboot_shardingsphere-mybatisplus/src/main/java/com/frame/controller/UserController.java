package com.frame.controller;


import com.frame.entity.User;
import com.frame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 接口测试
 *
 * @author wenbin
 * @date 2019/8/24 下午6:31
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Description: 批量保存用户
     */
    @PostMapping("save-user")
    public Object saveUser() {
        return userService.save(new User().setName("小李").setAge(11));
    }

    /**
     * @Description: 批量保存用户
     */
    @PostMapping("delete-user")
    public Object saveUser(Long id) {
        return userService.removeById(id);
    }
    /**
     * @Description: 获取用户列表
     */
    @GetMapping("list-user")
    public Object listUser() {
        return userService.list();
    }


}
