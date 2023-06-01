package cn.springmvc.mybatis.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.mybatis.entity.simple.News;
import cn.springmvc.mybatis.service.simple.NewsService;
import cn.springmvc.mybatis.web.command.NewsCommand;
import cn.springmvc.mybatis.web.util.WebUtil;
import cn.springmvc.mybatis.web.validator.NewsValidator;

/**
 * @author Vincent.wang
 *
 */
@Controller
public class NewsController {

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
        newsService.addNews(news, WebUtil.getUser());
        return "news/news_list";
    }

    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    public String search() {
        return "news/news_list";
    }

    @RequestMapping(value = "/news/list", method = RequestMethod.POST)
    @ResponseBody
    public List<News> search(@RequestParam(value = "keywords", required = false) String keywords) {
        return newsService.findNewsByKeywords(keywords);
    }
}
