package com.chentongwei.spider.service;

import com.chentongwei.spider.entity.Article;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: csdn文章
 */
public interface ArticleService {

    List<Article> forWeekly(Integer stage) throws Exception;

}
