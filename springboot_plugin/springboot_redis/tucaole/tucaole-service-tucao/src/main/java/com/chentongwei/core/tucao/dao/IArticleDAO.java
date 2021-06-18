package com.chentongwei.core.tucao.dao;

import com.chentongwei.core.tucao.entity.vo.article.ArticleDetailVO;
import com.chentongwei.core.tucao.entity.io.article.ArticleSaveIO;
import com.chentongwei.core.tucao.entity.io.article.ArticleUpdateIO;
import com.chentongwei.core.tucao.entity.vo.article.ArticleListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章DAO
 */
public interface IArticleDAO {

    /**
     * 文章详情
     *
     * @param id：文章id
     * @return
     */
    ArticleDetailVO getById(Long id);

    /**
     * 吐槽文章列表
     *
     * @return
     */
    List<ArticleListVO> list();

    /**
     * 吐槽文章+阅读数
     *
     * @param id：文章id
     * @param readCount：阅读数
     * @return
     */
    boolean updateReadCount(@Param("id") Long id, @Param("readCount") Integer readCount);

    /**
     * 作废吐槽文章
     *
     * @param id：文章id
     * @return
     */
    boolean updateDeleteStatus(Long id);

    /**
     * 更新吐槽文章
     * @param articleUpdateIO：参数
     * @return
     */
    boolean update(ArticleUpdateIO articleUpdateIO);

    /**
     * 发表吐槽文章
     *
     * @param articleSaveIO：参数
     * @return
     */
    boolean save(ArticleSaveIO articleSaveIO);
}