package cn.springmvc.mybatis.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.springmvc.mybatis.common.utils.salt.Digests;
import cn.springmvc.mybatis.common.utils.salt.Encodes;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.service.auth.UserService;
import cn.springmvc.mybatis.web.command.UserCommand;
import cn.springmvc.mybatis.web.util.WebUtil;
import cn.springmvc.mybatis.web.validator.UserValidator;

/**
 * @author Vincent.wang
 *
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") UserCommand user, BindingResult result, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (log.isDebugEnabled()) {
            log.debug("## 用户注册 , 用户 名：{}");
        }
        new UserValidator(UserValidator.LOGIN).validate(user, result);
        if (result.hasErrors()) {
            return "/user/register";
        }

        model.addAttribute("msg", "新增成功");
        return "user/register";
    }

    /**
     * 用户修改密码入口
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/updatepasswordPage", method = RequestMethod.GET)
    public String updatePassword(Model model) {
        return "user/updatePassword";
    }

    /**
     * 用户修改密码
     */
    @RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute("userCommand") UserCommand userCommand, BindingResult result, HttpServletRequest request, Model model) {
        if (log.isDebugEnabled()) {
            log.debug("## update password , name={}", userCommand.getUsername());
        }

        new UserValidator(UserValidator.UPDATE_PASSWORD).validate(userCommand, result);
        User user = WebUtil.getUser();
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "/user/updatePassword";
        }

        // check password
        User tempUser = new User();
        tempUser.setName(user.getName());
        tempUser.setPassword(userCommand.getPassword());
        tempUser.setSalt(user.getSalt());

        entryptPassword(tempUser);

        if (!StringUtils.equals(tempUser.getPassword(), user.getPassword())) {
            result.rejectValue("password", "user.password.error");
            model.addAttribute("user", user);
            return "/user/updatePassword";
        }

        userService.updatePassword(userCommand, WebUtil.getUser());
        return "redirect:/logout";
    }

    /**
     * 用户密码加密
     * 
     * @param user
     */
    private void entryptPassword(User user) {
        byte[] salt = Encodes.decodeHex(user.getSalt());
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, 1024);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }
}
