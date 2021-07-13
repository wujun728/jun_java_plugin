package com.ssm.dubbo.service.article.dao;

import com.ssm.dubbo.api.article.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
@Repository
public interface ArticleDao {

    /**
     * 返回相应的数据集合
     *
     * @param map
     * @return
     */
    List<Article> findArticles(Map<String, Object> map);

    /**
     * 数据数目
     *
     * @param map
     * @return
     */
    Long getTotalArticles(Map<String, Object> map);

    /**
     * 添加文章
     *
     * @return
     */
    int insertArticle(Article article);

    /**
     * 修改文章
     *
     * @return
     */
    int updArticle(Article article);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delArticle(String id);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    Article getArticleById(String id);
}
