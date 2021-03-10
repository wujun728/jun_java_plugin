package com.chentongwei.spider.action;

import com.chentongwei.spider.entity.Article;
import com.chentongwei.spider.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 测试
 */
@RestController
@RequestMapping("/test")
public class TestAction {

    @Resource
    private ArticleService articleService;

    @ResponseBody
    @GetMapping("/get/stage/{stage}")
    public List<Article> getArticleByStage(@PathVariable("stage") Integer stage) throws Exception {
        return articleService.forWeekly(stage);
    }
}
