package com.chentongwei.core.tucao.dao;

import com.chentongwei.core.tucao.entity.io.comment.ArticleCommentListIO;
import com.chentongwei.core.tucao.entity.io.comment.ArticleCommentSaveIO;
import com.chentongwei.core.tucao.entity.vo.comment.ArticleCommentListVO;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章评论DAO
 */
public interface IArticleCommentDAO {

    /**
     * 评论/回复列表
     *
     * @param articleCommentListIO：参数
     * @return
     */
    List<ArticleCommentListVO> list(ArticleCommentListIO articleCommentListIO);

    /**
     * 评论/回复保存
     *
     * @param articleCommentSaveIO：参数
     * @return
     */
    boolean save(ArticleCommentSaveIO articleCommentSaveIO);
}
