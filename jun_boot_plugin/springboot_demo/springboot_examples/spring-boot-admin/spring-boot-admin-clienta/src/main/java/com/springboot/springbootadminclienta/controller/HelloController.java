package com.springboot.springbootadminclienta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/10/1
 * @Time: 21:37
 * @email: inwsy@hotmail.com
 * Description:
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "http://www.geekdigging.com/";
    }
}
