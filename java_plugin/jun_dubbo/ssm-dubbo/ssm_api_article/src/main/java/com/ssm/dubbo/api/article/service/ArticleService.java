package com.ssm.dubbo.api.article.service;


import com.ssm.dubbo.api.article.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
 public interface ArticleService {
    /**
     * 返回相应的数据集合
     *
     * @param map
     * @return
     */
     List<Article> findArticle(Map<String, Object> map);

    /**
     * 数据数目
     *
     * @param map
     * @return
     */
     Long getTotalArticle(Map<String, Object> map);

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
     int addArticle(Article article);

    /**
     * 修改文章
     *
     * @param article
     * @return
     */
     int updateArticle(Article article);

    /**
     * 删除
     *
     * @param id
     * @return
     */
     int deleteArticle(String id);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
     Article findById(String id);
}
