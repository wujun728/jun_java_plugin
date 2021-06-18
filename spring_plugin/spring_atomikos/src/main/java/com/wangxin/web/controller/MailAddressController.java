package com.wangxin.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wangxin.common.constants.MailAddressEnum;
import com.wangxin.entity.mail.MailAddress;
import com.wangxin.service.mail.MailAddressService;

@Controller
public class MailAddressController {

    private static final Logger log = LoggerFactory.getLogger(MailAddressController.class);

    @Autowired
    private MailAddressService mailAddressService;

    /**
     * @Description 进入新增页面
     * @author 王鑫
     * @return
     */
    @RequestMapping(value = "/mail/add", method = RequestMethod.GET)
    public String add() {
        log.info("# 进入新增页面");
        return "mail/add";
    }

    /**
     * @Description ajax新增邮件配置信息
     * @author 王鑫
     * @param news
     * @return
     */
    @RequestMapping(value = "/mail/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(@ModelAttribute("mailAddressForm") MailAddress mailAddress) {
        boolean flag = mailAddressService.addMailAddress(mailAddress);
        Map<String, String> result = new HashMap<String, String>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "发布成功");
        } else {
            result.put("status", "0");
            result.put("msg", "发布失败");
        }
        return result;
    }

    /**
     * @Description ajax加载邮件配置对象
     * @author 王鑫
     * @return
     */
    @RequestMapping(value = "/mail/load/{id}", method = RequestMethod.GET)
    public String load(@PathVariable String id, ModelMap map) {
        log.info("# ajax加载邮件配置对象象");
        MailAddress mailAddress = mailAddressService.findMailAddressById(id);
        Map<String,String> mailAddressTypeMap = MailAddressEnum.getMailAddressType();
        map.addAttribute("ma", mailAddress);
        map.put("mailAddressTypeMap", mailAddressTypeMap);
        return "mail/edit_form";
    }

    /**
     * @Description ajax保存更新重新发布新闻
     * @author 王鑫
     * @param news
     * @return
     */
    @RequestMapping(value = "/mail/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(@ModelAttribute("mailAddressForm") MailAddress mailAddress) {
        boolean flag = mailAddressService.editMailAddress(mailAddress);
        Map<String, String> result = new HashMap<String, String>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "发布成功");
        } else {
            result.put("status", "0");
            result.put("msg", "发布失败");
        }
        return result;
    }

    @RequestMapping(value = "/mail/list", method = RequestMethod.GET)
    public String list(ModelMap map) {
        PageInfo<MailAddress> page = mailAddressService.findMailAddessByPage(null, null);
        map.put("page", page);
        return "mail/list";
    }

    @RequestMapping(value = "/mail/list_page", method = RequestMethod.POST)
    public String list_page(@RequestParam(value = "mailType", required = false) String mailType, @RequestParam(value = "pageNum", required = false) Integer pageNum, ModelMap map) {
        log.info("#分页  pageNum={} , mailType={}", pageNum, mailType);
        PageInfo<MailAddress> page = mailAddressService.findMailAddessByPage(pageNum, mailType);
        map.put("page", page);
        map.put("mailType", mailType);
        return "mail/list_page";
    }

}
