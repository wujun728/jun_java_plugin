package com.chentongwei.spider.service.impl;

import com.chentongwei.spider.analyzer.impl.CsdnWeeklyDocumentAnalyzer;
import com.chentongwei.spider.entity.Article;
import com.chentongwei.spider.service.ArticleService;
import com.chentongwei.spider.utils.ArticleSpider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: csdn文章实现
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private CsdnWeeklyDocumentAnalyzer csdnWeeklyDocumentAnalyzer;
    /**
     * 根据期号获取文章列表
     *
     * @param stage 期号
     * @return 文章列表
     */
    @Override
    public List<Article> forWeekly(Integer stage) throws Exception {
        List<Article> articleList = ArticleSpider.forEntityList("http://lib.csdn.net/weekly/" + stage, csdnWeeklyDocumentAnalyzer, Article.class);
        articleList.forEach(article -> article.setStage(stage));
        return articleList;
    }
}
