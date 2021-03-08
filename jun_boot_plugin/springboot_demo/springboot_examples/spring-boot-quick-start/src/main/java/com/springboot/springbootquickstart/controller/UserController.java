package com.springboot.springbootquickstart.controller;

import com.springboot.springbootquickstart.model.UserModel;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/11
 * @Time: 22:15
 * @email: inwsy@hotmail.com
 * Description:
 */
@RestController
public class UserController {

    // 创建线程安全的Map，用作数据存储
    static Map<Long, UserModel> users = new ConcurrentHashMap<Long, UserModel>();

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/")
    public List<UserModel> getUserList() {
        List<UserModel> list = new ArrayList<UserModel>(users.values());
        return list;
    }

    /**
     * 创建User
     * @param userModel
     * @return
     */
    @PostMapping("/")
    public UserModel postUser(@ModelAttribute UserModel userModel) {
        users.put(userModel.getId(), userModel);
        return users.get(userModel.getId());
    }

    /**
     * {id} 根据 url 中的 id 获取 user 信息
     * url中的id可通过@PathVariable绑定到函数的参数中
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {
        return users.get(id);
    }

    /**
     * 根据 id 更新用户信息
     * @param id
     * @param userModel
     * @return
     */
    @PutMapping("/{id}")
    public UserModel putUser(@PathVariable Long id, @ModelAttribute UserModel userModel) {
        UserModel u = users.get(id);
        u.setName(userModel.getName());
        u.setAge(userModel.getAge());
        users.put(id, u);
        return users.get(userModel.getId());
    }

    /**
     * 根据 id 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }
}
