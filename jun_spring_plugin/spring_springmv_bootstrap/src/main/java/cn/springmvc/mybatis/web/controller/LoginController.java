package cn.springmvc.mybatis.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.service.auth.UserService;
import cn.springmvc.mybatis.web.command.UserCommand;
import cn.springmvc.mybatis.web.util.WebUtil;
import cn.springmvc.mybatis.web.validator.UserValidator;

/**
 * @author Wujun
 *
 */
@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(@ModelAttribute("userCommand") UserCommand userCommand, BindingResult result) {
        log.info("# 进入登录页面...");
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            log.error("# 非法登录系统，请先登录。");
            return "login";
        }

        new UserValidator(UserValidator.LOGIN).validate(userCommand, result);
        User user = WebUtil.getUser();
        if (user.getStatus() != 1) {
            result.reject("user.lock.error");
            subject.logout();
            return "login";
        }

        userService.updateUserLastLoginTime(WebUtil.getUser());
        return "redirect:/index";
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute("userCommand") UserCommand userCommand, BindingResult result) {
        log.info("# 用户登录操作 ...");
        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            result.reject("user.login.error");
            return "login";
        }

        new UserValidator(UserValidator.LOGIN).validate(userCommand, result);
        User user = WebUtil.getUser();
        if (user.getStatus() != 1) {
            result.reject("user.lock.error");
            subject.logout();
            return "login";
        }

        userService.updateUserLastLoginTime(WebUtil.getUser());
        return "redirect:/index";
    }

}
