package com.xia.controller;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.LogManager;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request, String name, String password) {
        logger.info("管理员尝试登陆: name = " + name);
        try {
            if (StringUtils.isEmpty(name)||StringUtils.isEmpty(password)) {
                logger.error("用户名或密码不能为空！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "登录成功";
    }

}
