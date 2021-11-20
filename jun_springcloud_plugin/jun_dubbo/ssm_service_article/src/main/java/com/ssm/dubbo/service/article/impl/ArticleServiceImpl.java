package com.ssm.dubbo.service.article.impl;

import com.ssm.dubbo.api.article.entity.Article;
import com.ssm.dubbo.api.article.service.ArticleService;
import com.ssm.dubbo.service.article.dao.ArticleDao;
import com.ssm.dubbo.util.AntiXssUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    private static final Logger log = Logger.getLogger(ArticleService.class);

    @Resource
    private ArticleDao articleDao;

    @Override
    public List<Article> findArticle(Map<String, Object> map) {
        return articleDao.findArticles(map);
    }

    @Override
    public Long getTotalArticle(Map<String, Object> map) {
        return articleDao.getTotalArticles(map);
    }

    @Override
    public int addArticle(Article article) {
        if (article.getArticleTitle() == null || article.getArticleContent() == null || getTotalArticle(null) > 90 || article.getArticleContent().length() > 50000) {
            return 0;
        }
        article.setArticleTitle(AntiXssUtil.replaceHtmlCode(article.getArticleTitle()));
        if (articleDao.insertArticle(article) > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int updateArticle(Article article) {
        if (article.getArticleTitle() == null || article.getArticleContent() == null || getTotalArticle(null) > 90 || article.getArticleContent().length() > 50000) {
            return 0;
        }
        article.setArticleTitle(AntiXssUtil.replaceHtmlCode(article.getArticleTitle()));
        if (articleDao.updArticle(article) > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteArticle(String id) {
        return articleDao.delArticle(id);
    }

    @Override
    public Article findById(String id) {
        Article articleFromMysql = articleDao.getArticleById(id);
        if (articleFromMysql != null) {
            return articleFromMysql;
        }
        return null;
    }

}