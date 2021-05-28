package cn.springboot.controller;

import cn.springboot.model.simple.News;
import cn.springboot.service.simple.NewsService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * @Description 新闻示例
 * @author Wujun
 * @date Mar 16, 2017 3:58:01 PM  
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /** 
     * @Description 进入新增页面
     * @author Wujun
     * @return  
     */
    @RequestMapping(value = "/news/add", method = RequestMethod.GET)
    public String add() {
        log.info("# 进入发布新闻页面");
        return "view/news/add";
    }

    /** 
     * @Description ajax保存发布新闻
     * @author Wujun
     * @param news
     * @return  
     */
    @RequestMapping(value = "/news/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(@ModelAttribute("newsForm") News news) {
        boolean flag = newsService.addNews(news);
        Map<String, String> result = new HashMap<>();
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
     * @Description ajax加载新闻对象
     * @author Wujun
     * @return  
     */
    @RequestMapping(value = "/news/load/{id}", method = RequestMethod.GET)
    public String load(@PathVariable String id, ModelMap map) {
        log.info("# ajax加载新闻对象");
        News news = newsService.findNewsById(id);
        map.addAttribute("news", news);
        return "view/news/edit_form";
    }

    /**
     * @Description ajax保存更新重新发布新闻
     * @author Wujun
     * @param news
     * @return
     */
    @RequestMapping(value = "/news/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(@ModelAttribute("newsForm") News news) {
        boolean flag = newsService.editNews(news);
        Map<String, String> result = new HashMap<>();
        if (flag) {
            result.put("status", "1");
            result.put("msg", "发布成功");
        } else {
            result.put("status", "0");
            result.put("msg", "发布失败");
        }
        return result;
    }

    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    public String list(ModelMap map) {
        PageInfo<News> page = newsService.findNewsByPage(null, null);
        map.put("page", page);
        return "view/news/list";
    }

    @RequestMapping(value = "/news/list_page", method = RequestMethod.POST)
    public String list_page(@RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNum", required = false) Integer pageNum, ModelMap map) {
        log.info("#分页查询新闻 pageNum={} , keywords={}", pageNum, keywords);
        PageInfo<News> page = newsService.findNewsByPage(pageNum, keywords);
        map.put("page", page);
        map.put("keywords", keywords);
        return "view/news/list_page";
    }

    @RequestMapping(value = "/news/list1", method = RequestMethod.GET)
    public String list1(ModelMap map) {
        log.info("#分页查询数据库1");
        PageInfo<News> page = newsService.findNewsByPage1(null, null);
        map.put("page", page);
        return "view/news/list1";
    }

    @RequestMapping(value = "/news/list_page1", method = RequestMethod.POST)
    public String list_page1(@RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNum", required = false) Integer pageNum, ModelMap map) {
        log.info("#分页查询数据库2 pageNum={} , keywords={}", pageNum, keywords);
        PageInfo<News> page = newsService.findNewsByPage1(pageNum, keywords);
        map.put("page", page);
        map.put("keywords", keywords);
        return "view/news/list_page1";
    }

    @RequestMapping(value = "/news/list2", method = RequestMethod.GET)
    public String list2(ModelMap map) {
        log.info("#分页查询数据库2");
        PageInfo<News> page = newsService.findNewsByPage2(null, null);
        map.put("page", page);
        return "view/news/list2";
    }

    @RequestMapping(value = "/news/list_page2", method = RequestMethod.POST)
    public String list_page2(@RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "pageNum", required = false) Integer pageNum, ModelMap map) {
        log.info("#分页查询数据库2 pageNum={} , keywords={}", pageNum, keywords);
        PageInfo<News> page = newsService.findNewsByPage2(pageNum, keywords);
        map.put("page", page);
        map.put("keywords", keywords);
        return "view/news/list_page2";
    }

}
