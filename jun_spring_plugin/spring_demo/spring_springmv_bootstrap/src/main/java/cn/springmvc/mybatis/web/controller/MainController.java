package cn.springmvc.mybatis.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.springmvc.mybatis.common.constants.Constants;
import cn.springmvc.mybatis.common.exception.BusinessException;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.service.auth.UserService;
import cn.springmvc.mybatis.web.command.UserCommand;
import cn.springmvc.mybatis.web.util.WebUtil;

/**
 * @author Vincent.wang
 *
 */
@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public String index(@ModelAttribute("userCommand") UserCommand userCommand, BindingResult result, Model model) throws BusinessException {
        User user = WebUtil.getUser();
        if (user.getStatus() != 1) {
            // 用户被锁住，重定向到登录页面
            return "redirect:/login";
        }
        Session session = SecurityUtils.getSubject().getSession();
        // 判断是否是刚刚登录成功，若是刚刚登录成功，则存入session_key,并更新用户最后登录时间
        if (session.getAttribute(Constants.SESSION_KEY) == null) {
            if (log.isDebugEnabled()) {
                log.debug("## 记录用户登录时间");
            }
            userService.updateUserLastLoginTime(WebUtil.getUser());
            session.setAttribute(Constants.SESSION_KEY, user);
        }
        return "index";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error403() {
        return "/common/403";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "/common/404";
    }

}
