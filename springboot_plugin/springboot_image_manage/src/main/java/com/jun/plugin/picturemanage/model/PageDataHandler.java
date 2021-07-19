package com.jun.plugin.picturemanage.model;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.picturemanage.annotation.PageUrl;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/1 10:44
 * 固定格式：
 * pages/constant
 */
@Component
public class PageDataHandler {


    @PageUrl("pages/console")
    public void pageTable(ModelAndView modelAndView) {
        modelAndView.addObject("name", "Chenzedeng");
    }

}
