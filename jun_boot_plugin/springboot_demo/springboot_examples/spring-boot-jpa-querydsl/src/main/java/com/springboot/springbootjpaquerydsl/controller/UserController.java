package com.springboot.springbootjpaquerydsl.controller;

import com.springboot.springbootjpaquerydsl.dto.UserDTO;
import com.springboot.springbootjpaquerydsl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/26
 * @Time: 0:22
 * @email: inwsy@hotmail.com
 * Description:
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/userDto")
    public List<UserDTO> userDto() {
        return userService.selectAllUserDTOList();
    }
}
