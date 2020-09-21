package com.chentongwei.spider.service;

import com.chentongwei.spider.entity.Article;

import java.util.List;

/**
 * @author TongWei.Chen 2017-10-25 10:51:27
 * @Project tucaole
 * @Description: csdn文章
 */
public interface ArticleService {

    List<Article> forWeekly(Integer stage) throws Exception;

}
