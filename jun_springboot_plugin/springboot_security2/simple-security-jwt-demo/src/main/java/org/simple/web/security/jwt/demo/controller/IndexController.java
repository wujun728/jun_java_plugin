package org.simple.web.security.jwt.demo.controller;

import org.simple.web.security.jwt.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 项目名称：web-security-jwt
 * 类名称：IndexController
 * 类描述：IndexController
 * 创建时间：2018/9/12
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@RequestMapping("/index")
@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index(Principal principal) {
        return "index";
    }

}
