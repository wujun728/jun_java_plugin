package com.wx.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wx.entity.simple.News;
import com.wx.service.simple.NewsService;
import com.wx.web.command.NewsCommand;
import com.wx.web.validator.NewsValidator;

/**
 * @author Vincent.wang
 *
 */
@Controller
public class NewsController {

    private static final Logger log = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    /*
     * 表单提交日期绑定
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String addNews() {
        return "news/news";
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public String add(@ModelAttribute("mewsCommand") NewsCommand command, BindingResult result) {
        new NewsValidator().validate(command, result);
        if (result.hasErrors()) {
            return "news/news";
        }
        News news = new News();
        news.setTitle(command.getTitle());
        news.setDescription(command.getDescription());
        news.setAddress(command.getAddress());
        news.setNewsTime(command.getNewsTime());
        newsService.addNews(news);
        return "news/news_list";
    }

    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    public String search() {
        log.info("# 进入新闻列表页...");
        return "news/news_list";
    }

    @RequestMapping(value = "/news/list", method = RequestMethod.POST)
    public String search(@RequestParam(value = "keywords", required = false) String keywords, ModelMap model) {
        model.addAttribute("list", newsService.findNewsByKeywords(keywords));
        return "news/news_list_body";
    }
}
