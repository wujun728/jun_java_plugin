package com.jun.plugin.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jun.plugin.mongodb.model.Article;

import java.util.List;

/**
 * <p>
 * 文章 Dao
 * </p>
 *
 * @package: com.xkcoding.mongodb.repository
 * @description: 文章 Dao
 * @author: yangkai.shen
 * @date: Created in 2018-12-28 16:30
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface ArticleRepository extends MongoRepository<Article, Long> {
    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @return 满足条件的文章列表
     */
    List<Article> findByTitleLike(String title);
}
