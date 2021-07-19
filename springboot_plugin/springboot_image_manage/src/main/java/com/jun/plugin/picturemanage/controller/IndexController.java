package com.jun.plugin.picturemanage.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.picturemanage.conf.Constant;
import com.jun.plugin.picturemanage.conf.CustomException;
import com.jun.plugin.picturemanage.conf.InitState;
import com.jun.plugin.picturemanage.entity.UserEntity;
import com.jun.plugin.picturemanage.service.ConfService;
import com.jun.plugin.picturemanage.util.*;

import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 17:22
 */
@Controller
public class IndexController {


    @Autowired
    private  ConfService confService;

    @RequestMapping("/main/index")
    public String index() {
        return "index";
    }


    @RequestMapping("/init/setup")
    public String setup(Model model) {
        model.addAttribute("env", "docker--");
        if (InitState.IS_INIT && StringUtils.isNotBlank(Constant.ROOT_DIR)) {
            return "redirect:/login";
        }
        return "setup";
    }

    /**
     * 设置URL访问前缀
     *
     * @param prefix
     * @return
     */
    @PostMapping("/init/config")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public JsonResult setConf(String prefix, String rootPath) {
        if (InitState.IS_INIT && StringUtils.isNotBlank(Constant.ROOT_DIR)) {
            return JsonResult.error("已经初始化");
        }
        if (StringUtils.isBlank(prefix) || StringUtils.isBlank(rootPath)) {
            return JsonResult.errorForEmpty();
        }

        //检查文件夹是否有错误
        File file = new File(rootPath);
        if (file == null || !file.isDirectory()) {
            return JsonResult.error("此文件不存在或者不是个文件夹");
        }

        if (!prefix.startsWith("http")) {
            prefix = "http://" + prefix;
        }
        boolean state = confService.set(Constant.URL_PREFIX_KEY_NAME, prefix);

        if (!state) {
            return JsonResult.error("初始化失败");
        }

        state = confService.set(Constant.FILE_ROOT_KEY_NAME, file.getAbsolutePath());
        if (!state) {
            throw new CustomException("初始化失败");
        }

        InitState.URL_PREFIX = prefix;
        InitState.IS_INIT = true;
        Constant.ROOT_DIR = file.getAbsolutePath();

        return JsonResult.actionSuccess();
    }


    /**
     * 获取基础配置信息
     *
     * @return
     */
    @GetMapping("/conf/getBaseConf")
    @ResponseBody
    public JsonResult getBaseConf() {
        CustomMap map = CustomMap.create();
        map.put("prefix", confService.get(Constant.URL_PREFIX_KEY_NAME));
        map.put("uuid", Boolean.valueOf(confService.get(Constant.UUID_FILE_NAME_SWITCH_KEY_NAME, "false")));
        return JsonResult.success(map);
    }

    /**
     * 配置基础配置
     *
     * @param prefix
     * @param uuid
     * @return
     */
    @PostMapping("/conf/setBaseConf")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public JsonResult setBaseConf(String prefix, boolean uuid) {
        if (StringUtils.isBlank(prefix)) {
            return JsonResult.errorForEmpty();
        }
        if (!prefix.startsWith("http")) {
            prefix = "http://" + prefix;
        }
        boolean state = confService.set(Constant.URL_PREFIX_KEY_NAME, prefix);
        if (state) {
            state = confService.set(Constant.UUID_FILE_NAME_SWITCH_KEY_NAME, String.valueOf(uuid));
        }

        if (!state) {
            throw new CustomException("配置失败");
        }
        return JsonResult.actionSuccess();
    }


    @RequestMapping("/login")
    public String login(HttpSession session) {
        session.removeAttribute(Constant.LOGIN_STATE);
        return "login";
    }


    /**
     * 登陆的操作
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login/post")
    @ResponseBody
    public JsonResult loginPost(String username, String password, HttpSession session) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JsonResult.errorForEmpty();
        }

        UserEntity userInfo = UserUtil.getUserInfo();

        if (userInfo.getUsername().equals(username) && userInfo.getPassword().equals(password)) {
            session.setAttribute(Constant.LOGIN_STATE, userInfo);
            return JsonResult.actionSuccess();
        }
        return JsonResult.error("用户名或者密码错误");
    }

    /**
     * 修改安全设置
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping("/updateSecurity")
    @ResponseBody
    public JsonResult updateSecurity(String oldPwd, String newPwd) {
        if (StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd)) {
            return JsonResult.errorForEmpty();
        }

        UserEntity userInfo = UserUtil.getUserInfo();
        if (!userInfo.getPassword().equals(oldPwd)) {
            return JsonResult.error("原密码输入错误");
        }

        UserEntity info = UserUtil.setUserInfo(userInfo.getUsername(), newPwd);
        if (info == null) {
            return JsonResult.actionFailure();
        }
        return JsonResult.actionSuccess();
    }


    /**
     * 返回视图解析
     *
     * @param name1
     * @param name2
     * @return
     */
    @RequestMapping(value = {"/main/pages/{name1}/{name2}", "/main/pages/{name1}"})
    public ModelAndView pages(@PathVariable("name1") String name1, @PathVariable(value = "name2", required = false) String name2) {
        ModelAndView modelAndView = new ModelAndView();
        String link = null;
        if (StringUtils.isBlank(name2)) {
            link = String.format("pages/%s", name1);
        } else {
            link = String.format("pages/%s/%s", name1, name2);
        }
        modelAndView.setViewName(link);
        //调用Handler处理器填充数据集
        ModelDataUtil.setData(modelAndView, link);
        return modelAndView;
    }


}
