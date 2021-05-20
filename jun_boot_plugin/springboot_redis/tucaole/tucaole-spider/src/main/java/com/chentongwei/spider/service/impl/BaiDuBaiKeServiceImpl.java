package com.chentongwei.spider.service.impl;

import com.chentongwei.spider.analyzer.impl.BaiduBKWeeklyDocumentAnalyzer;
import com.chentongwei.spider.entity.Article;
import com.chentongwei.spider.service.BaiDuBaiKeService;
import com.chentongwei.spider.utils.ArticleSpider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 百度百科实现
 */
@Service
public class BaiDuBaiKeServiceImpl implements BaiDuBaiKeService {
    @Resource
    private BaiduBKWeeklyDocumentAnalyzer baiduBKWeeklyDocumentAnalyzer;

    @Override
    public List forWeekly(Integer stage) throws Exception {
        List<Article> articleList = ArticleSpider.forEntityList("https://baike.baidu.com/item/%E7%99%BD%E9%B2%9C%E7%9A%AE/16010177?qq-pf-to=pcqq.c2c", baiduBKWeeklyDocumentAnalyzer, Article.class);
        articleList.forEach(article -> article.setStage(stage));
        return articleList;
    }
}
